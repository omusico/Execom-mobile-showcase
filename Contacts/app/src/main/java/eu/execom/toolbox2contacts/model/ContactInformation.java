package eu.execom.toolbox2contacts.model;

import eu.execom.toolbox2contacts.R;
import eu.execom.toolbox2contacts.Toolbox;

public class ContactInformation {

    public enum Category {
        PRIVATE(R.string.info_private),
        WORK(R.string.info_work),
        OTHER(R.string.info_other);

        private int stringId;

        Category(int stringId) {
            this.stringId = stringId;
        }

        public int getStringId() {
            return stringId;
        }

        @Override
        public String toString() {
            return Toolbox.instance.getString(stringId);
        }
    }

    public enum Type {
        PHONE(R.string.phone, R.drawable.phone),
        EMAIL(R.string.email, R.drawable.email),
        ADDRESS(R.string.address, R.drawable.location),
        WEBSITE(R.string.website, R.drawable.website);

        private int imageId;
        private int stringId;

        Type (int stringId, int imageId){
            this.stringId = stringId;
            this.imageId = imageId;
        }

        public int getImageId() {
            return imageId;
        }

        public int getStringId() {
            return stringId;
        }

        @Override
        public String toString() {
            return Toolbox.instance.getString(stringId);
        }
    }

    public String id;
    public Type type;
    public Category category;
    public String value;
    public Contact contact;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

}
