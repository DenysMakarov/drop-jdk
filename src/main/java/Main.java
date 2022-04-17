import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    private static final String ACCESS_TOKEN = "sl.BF3ZR6zQlHG3oLdExoFwyX-ANbTUTqixkkE1-QMb1bYS3uByo4zx2SjyL8XgKnXhLWl2pwr8SkUpzOf4GrLFMPBzoUEcfGRk8BoWKFynLtVHtIQlXHCE9LZv0nQEwjjijyHQS7nTOxE";

    public static void main(String args[]) throws DbxException {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/mda_drop_test").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        FullAccount account = client.users().getCurrentAccount();
        System.out.println(account.getName().getDisplayName());


        ListFolderResult result = client.files().listFolder("");
        while (true) {

            for (Metadata metadata : result.getEntries()) {
                System.out.println(metadata.getPathLower());
            }

            if (!result.getHasMore()) {
                break;
            }
            result = client.files().listFolderContinue(result.getCursor());
        }

        try (InputStream in = new FileInputStream("./test.txt"))  {
            FileMetadata metadata = client.files().uploadBuilder("/test.txt").uploadAndFinish(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
