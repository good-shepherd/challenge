package com.midasit.challenge.services;

import com.midasit.challenge.entities.*;
import com.midasit.challenge.payloads.OpResponse;
import com.midasit.challenge.payloads.OrderRequest;
import com.midasit.challenge.payloads.OrderResponse;
import com.midasit.challenge.repositories.OrderProductRepository;
import com.midasit.challenge.repositories.OrderRepository;
import com.midasit.challenge.repositories.ProductRepository;
import com.midasit.challenge.repositories.UserRepository;
import com.midasit.challenge.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Slf4j
@AllArgsConstructor
@Service
public class OrderService {

    OrderRepository orderRepository;
    UserRepository userRepository;
    ProductRepository productRepository;
    OrderProductRepository opRepository;

    @Transactional
    public void placeOrder(OrderRequest orderRequest, UserPrincipal userPrincipal) {
        Order order = new Order(userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException()), false);
        orderRepository.save(order);
        List<String> productId = orderRequest.getProductId();
        List<String> quantity = orderRequest.getQuantity();
        for (int i = 0; i < productId.size(); i++) {
            System.out.println("product: " + productId.get(i) + ", quantity: " + quantity.get(i));
            Product product = productRepository.findById(Long.parseLong(productId.get(i))).orElseThrow(() -> new ResourceNotFoundException());
            // OrderProduct op = new OrderProduct(new CompositePK(order.getId(), product.getId()), order, product, Integer.parseInt(quantity.get(i)));
            OrderProduct op = new OrderProduct(new CompositePK(order.getId(), product.getId()), order, product, Integer.parseInt(quantity.get(i)));
            opRepository.save(op);
        }
        // notification needed + need to be refactored (String list)
    }

    public List<OrderResponse> findAllOrder() {
        List<Order> list = orderRepository.findAll();
        List<OrderResponse> or = new ArrayList<>();
        for (Order order : list) {
            List<OrderProduct> opList = new ArrayList<>(order.getOrderProducts());
            List<String> nameList = new ArrayList<>();
            List<Integer> qList = new ArrayList<>();
            for (OrderProduct orderProduct : opList) {
                nameList.add(orderProduct.getProduct().getProductName());
                qList.add(orderProduct.getQuantity());
            }
            or.add(new OrderResponse(order.getId(), order.getCustomer().getId(), order.getCustomer().getUserName(),
                    order.isDone(), getSumOfPrice(order), qList, nameList, order.getUpdatedDate()));
        }
        return or;
    }

    public List<OrderResponse> getNotDoneOrder() {
        List<OrderResponse> or = new ArrayList<>();
        List<Order> list = orderRepository.findAll();
        for (Order order : list) {
            if (!order.isDone()) {
                List<OrderProduct> opList = new ArrayList<>(order.getOrderProducts());
                List<String> nameList = new ArrayList<>();
                List<Integer> qList = new ArrayList<>();
                for (OrderProduct orderProduct : opList) {
                    nameList.add(orderProduct.getProduct().getProductName());
                    qList.add(orderProduct.getQuantity());
                }
                or.add(new OrderResponse(order.getId(), order.getCustomer().getId(), order.getCustomer().getUserName(),
                        order.isDone(), getSumOfPrice(order), qList, nameList, order.getUpdatedDate()));
            }
        }
        return or;
    }

    public OpResponse findOpById(Long id) {
        List<OrderProduct> op = opRepository.getOPs(id);
        OpResponse opr = new OpResponse();
        opr.setOrderId(id);
        List<Long> productIds = new ArrayList<>();
        List<String> productNames = new ArrayList<>();
        for (OrderProduct orderProduct : op) {
            productIds.add(orderProduct.getProduct().getId());
            productNames.add(orderProduct.getProduct().getProductName());
        }
        opr.setProductId(productIds);
        opr.setProductName(productNames);
        return opr;
    }


    @Transactional
    public void handleOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        order.setDone(true);
        orderRepository.save(order);
    }

    public void cancelOrder() {

    }

    public List<OrderResponse> findMyOrder(UserPrincipal userPrincipal) {
        List<OrderResponse> or = new ArrayList<>();
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException());
        List<Order> orders = new ArrayList<>(user.getOrders());
        for (Order order : orders) {
            List<OrderProduct> opList = new ArrayList<>(order.getOrderProducts());
            List<String> nameList = new ArrayList<>();
            List<Integer> qList = new ArrayList<>();
            for (OrderProduct orderProduct : opList) {
                nameList.add(orderProduct.getProduct().getProductName());
                qList.add(orderProduct.getQuantity());
            }
            or.add(new OrderResponse(order.getId(), order.getCustomer().getId(), order.getCustomer().getUserName(),
                    order.isDone(), getSumOfPrice(order), qList, nameList, order.getUpdatedDate()));
        }
        return or;
    }

    private int getSumOfPrice(Order order) {
        int sum = 0;
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            int price = orderProduct.getProduct().getProductPrice();
            int qt = orderProduct.getQuantity();
            sum += price * qt;
        }
        return sum;
    }
}
