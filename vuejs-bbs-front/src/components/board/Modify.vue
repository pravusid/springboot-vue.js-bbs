<script>
import axios from 'axios';
import notification from '../../lib/notification';
import Write from './Write.vue';

export default {
  mixins: [Write],

  created() {
    axios.get(`/api/v1/board/${this.$route.params.id}`).then((res) => {
      this.article.title = res.data.title;
      this.article.content = res.data.content;
    });
  },

  data: () => ({
    job: '게시물 수정',
  }),

  methods: {
    write() {
      axios.put(`/api/v1/board/${this.$route.params.id}`, this.article).then((res) => {
        notification.success(res, '게시물을 수정하였습니다', () => {
          this.$router.push({
            path: `/board/${this.$route.params.id}`, query: this.$route.query,
          });
        });
      }).catch((err) => {
        notification.error(err, '게시물 수정 실패!\n다시 시도해주세요');
      });
    },

    cancel() {
      this.$router.push({
        path: `/board/${this.$route.params.id}`, query: this.$route.query,
      });
    },
  },
};
</script>
