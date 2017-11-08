package tecnology.now.miniappgoals;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Alan on 08/11/2017.
 */

public class ApplicationBase extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            // The default Realm file is "default.realm" in Context.getFilesDir();
            // we'll change it to "myrealm.realm"
            Realm.init(this);
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .name("app-goals.realm")
                    .deleteRealmIfMigrationNeeded()
                    .schemaVersion(2)
                    .build();
            Realm.setDefaultConfiguration(config);
        }
}
