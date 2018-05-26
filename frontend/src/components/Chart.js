import { Line } from 'vue-chartjs';

export default {
  extends: Line,
  mounted() {
    this.renderChart({
      labels: ['1월', '2월', '3월', '4월', '5월', '6월', '7월'],
      datasets: [
        {
          label: 'Data One',
          backgroundColor: '#FC2525',
          data: [40, 39, 10, 40, 39, 80, 40],
        },
      ],
    }, { responsive: true, maintainAspectRatio: false });
  },
};
