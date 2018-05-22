package zia.bean;

import java.util.List;

public class RankGetResult {

    /**
     * status : 200
     * info : ok
     * data : [{"score":150,"username":"default","nickname":"zzzia","date":"May 1, 2018"},{"score":150,"username":"default","nickname":"zzzia","date":"May 1, 2018"}]
     */

    private int status;
    private String info;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "RankGetResult{" +
                "status=" + status +
                ", info='" + info + '\'' +
                ", data=" + data +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * score : 150
         * username : default
         * nickname : zzzia
         * date : May 1, 2018
         */

        private int score;
        private String username;
        private String nickname;
        private String date;

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "score=" + score +
                    ", username='" + username + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", date='" + date + '\'' +
                    '}';
        }
    }
}
