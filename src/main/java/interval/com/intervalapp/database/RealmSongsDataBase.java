package interval.com.intervalapp.database;

import java.util.List;

import interval.com.intervalapp.model.Song;
import io.realm.Realm;

public class RealmSongsDataBase {
    private Realm realm = Realm.getDefaultInstance();

    public void saveSongs(final List<Song> allSongs) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(allSongs);

            }
        });
    }

    public List<Song> readSongList(String type) {

        return realm.where(Song.class).equalTo("type",type).findAll();

    }

    public Song findByHash(int hash){
        for(Song s: realm.where(Song.class).findAll()){
            if(s.hashCode() == hash){
                return s;
            }
        }
        return null;
    }
}

