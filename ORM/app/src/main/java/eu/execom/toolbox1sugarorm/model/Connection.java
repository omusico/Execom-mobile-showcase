package eu.execom.toolbox1sugarorm.model;

import com.orm.SugarRecord;

public class Connection extends SugarRecord<Connection> {

    private User fromUser;
    private User toUser;

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "fromUser=" + fromUser +
                ", toUser=" + toUser +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection)) return false;

        Connection that = (Connection) o;

        if (!fromUser.equals(that.fromUser)) return false;
        return toUser.equals(that.toUser);

    }

    @Override
    public int hashCode() {
        int result = fromUser.hashCode();
        result = 31 * result + toUser.hashCode();
        return result;
    }

}
