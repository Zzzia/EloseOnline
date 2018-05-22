package zia.bean;

import java.util.Observable;

public class UserBean {

    /**
     * status : 200
     * info : ok
     * data : {"username":"jzljzljl","nickname":"zia","password":"jzljzljzl","createDate":"May 1, 2018"}
     */

    private int status;
    private String info;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean extends Observable {
        /**
         * username : jzljzljl
         * nickname : zia
         * password : jzljzljzl
         * createDate : May 1, 2018
         */

        private String username;
        private String nickname;
        private String password;
        private String createDate;

        public DataBean() {
            username = "";
            nickname = "";
            password = "";
            createDate = "";
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
            setChanged();
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "username='" + username + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", password='" + password + '\'' +
                    ", createDate='" + createDate + '\'' +
                    '}';
        }
    }
}
