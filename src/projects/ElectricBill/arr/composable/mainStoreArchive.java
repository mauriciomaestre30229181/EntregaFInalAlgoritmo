package ElectricBill.arr.composable;

import ElectricBill.arr.Repositories.User;
import ElectricBill.arr.Repositories.ArchiveUtil;

public class mainStoreArchive {
    public static void store(User user, ArchiveUtil archiveUtil) {
        storeArchive.saveUserBill(user, archiveUtil);
    }
}
