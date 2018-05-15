/**
 * Maze BlockPosition class is used to track the maze block position in mirror
 * maze as the laser light travel through the maze.
 * 
 * @author Bankim Aghera
 *
 */
public class BlockPosition {

	public static final String MOVE_FORWARD = "+";
	public static final String MOVE_BACKWARD = "-";

	public static final String ORIENTATION_HORIZONTAL = "H";
	public static final String ORIENTATION_VERTICAL = "V";

	/**
	 * Number of column in the maze room
	 */
	public int mColumn;

	/**
	 * Number of rows in the maze room
	 */
	public int mRow;

	/**
	 * Mirror Orientation, either Horizontal or Vertical Horizontal denoted by "H"
	 * Vertical denoted by "V"
	 */
	public String mOrientation;

	/**
	 * Laser Direction, either one step forward or one step backward
	 * 
	 * "+" indicates one step forward, increase one step "-" indicates one step
	 * backward, decrease one step
	 */
	public String mDirection; // "+": move forward one step; "-": move backward one step

	/**
	 * Constructs a new BlockPosition with the specified column (X), row (Y),
	 * orientation of the mirror (H or V), direction of the light (+1 or -1) path as
	 * it travel in the maze.
	 * 
	 * @param col
	 *            - the column position of the cell
	 * @param row
	 *            - the row position of the cell
	 * @param orientation
	 *            - orientation of the mirror (H or V)
	 * @param direction
	 *            - direction of the light (+1 or -1) path as it travel in the maze
	 */
	public BlockPosition(int col, int row, String orientation, String direction) {
		mColumn = col;
		mRow = row;

		if (!orientation.equals(BlockPosition.ORIENTATION_HORIZONTAL)
				&& !orientation.equals(BlockPosition.ORIENTATION_VERTICAL)) {
			try {
				throw new InvalidOrientationException("The " + orientation + " direction of mirror is not supported.");
			} catch (InvalidOrientationException e) {
				// The input mirror orientation is invalid
				// so correcting it by assuming Horizontal - "H" orientation (Default mirror
				// orientation)
				orientation = BlockPosition.ORIENTATION_HORIZONTAL;

				System.out.println("Input Autocorrection - Horizontal (default) mirror orientation applied");
			}
		}
		mOrientation = orientation;

		if (!direction.equals(BlockPosition.MOVE_FORWARD) && !direction.equals(BlockPosition.MOVE_BACKWARD)) {
			try {
				throw new InvalidDirectionException("The " + direction + " direction of movement is not supported.");
			} catch (InvalidDirectionException e) {
				// The input direction is invalid
				// so correcting it by assuming "+" - one step forward (Default mirror
				// direction)
				direction = BlockPosition.MOVE_FORWARD;

				System.out.println("Input Autocorrection - (+1 default) direction, increase one step, applied");
			}
		}
		mDirection = direction;
	}

	/**
	 * Returns a short description of this block Position. The result is the
	 * concatenation of column (X), row (Y), orientation of the mirror (H or V),
	 * direction of the light (+1 or -1) path as it travel in the maze.
	 * 
	 * @return <code>String</code> based on default encoding of the JVM
	 */
	public String toString() {
		return "Maze Block position X,Y : " + mColumn + ", " + mRow + " ( orientation: " + mOrientation + ", direction: "
				+ mDirection + " ) ";
	}

	/**
	 * Indicates whether some other object is "equal to" this one.<br>
	 * The equals method implements an equivalence relation on non-null object
	 * references:<br>
	 * <ul>
	 * <li>It is reflexive: for any non-null reference value x, x.equals(x) should
	 * return true.
	 * <li>It is symmetric: for any non-null reference values x and y, x.equals(y)
	 * should return true if and only if y.equals(x) returns true.
	 * <li>It is transitive: for any non-null reference values x, y, and z, if
	 * x.equals(y) returns true and y.equals(z) returns true, then x.equals(z)
	 * should return true.
	 * <li>It is consistent: for any non-null reference values x and y, multiple
	 * invocations of x.equals(y) consistently return true or consistently return
	 * false, provided no information used in equals comparisons on the objects is
	 * modified.
	 * <li>For any non-null reference value x, x.equals(null) should return false.
	 * <br>
	 * <p>
	 * The equals method for class Object implements the most discriminating
	 * possible equivalence relation on objects; that is, for any non-null reference
	 * values x and y, this method returns true if and only if x and y refer to the
	 * same object (x == y has the value true).
	 * </p>
	 * <p>
	 * <b>Note:</b> that it is generally necessary to override the hashCode method
	 * whenever this method is overridden, so as to maintain the general contract
	 * for the hashCode method, which states that equal objects must have equal hash
	 * codes.
	 * </p>
	 * 
	 * @param obj
	 *            - the reference object with which to compare.
	 * @return <code>true</code> if this object is the same as the <code>obj</code>
	 *         argument; <code>false</code> otherwise.
	 * 
	 */
	public boolean equals(BlockPosition obj) {
		if (mColumn == obj.mColumn && mRow == obj.mRow && mOrientation.equals(obj.mOrientation)
				&& mDirection.equals(obj.mDirection)) {
			return true;
		} else {
			return false;
		}
	}

}