package yevhent.project.wikimedia.model;

import org.json.JSONObject;

public class WikimediaRecentchange {

    private final long id;
    private final long timestamp;

    private final boolean isMinor;
    private final boolean isBot;
    private final boolean isPatrolled;

    private final long newRevision;
    private final long oldRevision;

    private final int newLength;
    private final int oldLength;

    private final String meta;
    private final String type;
    private final String user;
    private final String title;
    private final String titleUrl;
    private final String notifyUrl;
    private final String comment;
    private final String parsedComment;

    public WikimediaRecentchange(String data) {
        JSONObject jsonObject = new JSONObject(data);
        this.id = jsonObject.optLong("id");
        this.timestamp = jsonObject.optLong("timestamp");
        this.isMinor = jsonObject.optBoolean("minor");
        this.isBot = jsonObject.optBoolean("bot");
        this.isPatrolled = jsonObject.optBoolean("patrolled");
        this.newRevision = jsonObject.optJSONObject("revision", new JSONObject("{}")).optLong("new");
        this.oldRevision = jsonObject.optJSONObject("revision", new JSONObject("{}")).optLong("old");
        this.newLength = jsonObject.optJSONObject("length", new JSONObject("{}")).optInt("new");
        this.oldLength = jsonObject.optJSONObject("length", new JSONObject("{}")).optInt("old");
        this.meta = jsonObject.optJSONObject("meta", new JSONObject("{}")).toString();
        this.type = jsonObject.optString("type");
        this.user = jsonObject.optString("user");
        this.title = jsonObject.optString("title");
        this.titleUrl = jsonObject.optString("title_url");
        this.notifyUrl = jsonObject.optString("notify_url");
        this.comment = jsonObject.optString("comment");
        this.parsedComment = jsonObject.optString("parsedcomment");
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "\n\tid=" + id +
                ", timestamp=" + timestamp +
                ", isMinor=" + isMinor +
                ", isBot=" + isBot +
                ", isPatrolled=" + isPatrolled +
                ", newRevision=" + newRevision +
                ", oldRevision=" + oldRevision +
                ", newLength=" + newLength +
                ", oldLength=" + oldLength +
                ",\n\tmeta='" + meta + '\'' +
                ",\n\ttype='" + type + '\'' +
                ", user='" + user + '\'' +
                ", title='" + title + '\'' +
                ", titleUrl='" + titleUrl + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ",\n\tcomment='" + comment + '\'' +
                ", parsedComment='" + parsedComment + '\'' +
                '}';
    }

    public String toJson(){
        return new JSONObject(this).toString();
    }

    public long getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isMinor() {
        return isMinor;
    }

    public boolean isBot() {
        return isBot;
    }

    public boolean isPatrolled() {
        return isPatrolled;
    }

    public long getNewRevision() {
        return newRevision;
    }

    public long getOldRevision() {
        return oldRevision;
    }

    public int getNewLength() {
        return newLength;
    }

    public int getOldLength() {
        return oldLength;
    }

    public String getMeta() {
        return meta;
    }

    public String getType() {
        return type;
    }

    public String getUser() {
        return user;
    }

    public String getTitleUrl() {
        return titleUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public String getComment() {
        return comment;
    }

    public String getParsedComment() {
        return parsedComment;
    }
}
