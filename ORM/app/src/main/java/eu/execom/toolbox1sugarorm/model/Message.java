package eu.execom.toolbox1sugarorm.model;

import com.orm.SugarRecord;

public class Message extends SugarRecord<Message> {

    private String text;
    private User author;
    private long dateTime;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", author=" + author +
                ", dateTime=" + dateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;

        Message message = (Message) o;

        if (dateTime != message.dateTime) return false;
        if (!text.equals(message.text)) return false;
        return author.equals(message.author);

    }

    @Override
    public int hashCode() {
        int result = text.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + (int) (dateTime ^ (dateTime >>> 32));
        return result;
    }

}
