package interval.com.intervalapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Song extends RealmObject {

    private static final String FAST = "fast";
    private static final String SLOW = "slow";

    @Setter
    private String tittle;
    @PrimaryKey
    private String uri;

    @Setter
    private String type;

    public Song(String tittle, String uri, String type) {
        this.tittle = tittle;
        this.uri = uri;
        this.type = type;
    }

    public Song(String tittle, String uri) {
        this.tittle = tittle;
        this.uri = uri;
        this.type = FAST;
    }

    public Song() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;

        Song song = (Song) o;

        if (getTittle() != null ? !getTittle().equals(song.getTittle()) : song.getTittle() != null)
            return false;
        if (getUri() != null ? !getUri().equals(song.getUri()) : song.getUri() != null)
            return false;
        return getType() != null ? getType().equals(song.getType()) : song.getType() == null;

    }

    @Override
    public int hashCode() {
        int result = getTittle() != null ? getTittle().hashCode() : 0;
        result = 31 * result + (getUri() != null ? getUri().hashCode() : 0);
        return result;
    }
}
