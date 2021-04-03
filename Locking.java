
import java.util.Vector;

public class Locking {
	public static Vector<Long> locklist = new Vector<Long>();

	public static Integer i = 1;

	public static synchronized void lock(long id) {
		try {

			while (addRemoveList(id, true)) {
				Locking.class.wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public synchronized static boolean addRemoveList(long id, boolean add) {
		boolean locked = false;
		if (add) {
			if (locklist.size() == 0) {
				locklist.add(id);
			} else {
				boolean validator = true;
				for (Long i : locklist) {
					if (i == id) {
						locked = true;
						validator = false;
					}
				}
				if (validator) {
					locklist.add(id);
				}
			}
		} else {
			Vector<Long> locklistnew = new Vector<Long>();
			for (Long l : locklist) {
				if (l != id) {
					locklistnew.add(l);
				}
			}
			locklist = locklistnew;

		}

		return locked;
	}

	public static synchronized void unlock(long id) {
		addRemoveList(id, false);
		Locking.class.notify();
	}
}