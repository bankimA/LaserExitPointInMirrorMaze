/**
 * Maze Mirror class represent the each mirror placed at the center of the maze
 * block.
 * 
 * The mirror has following properties:
 * <ul>
 * <li>Direction of Leaning (R for Right and L for Left)
 * <li>Reflective Side of the mirror (R for Right Side or L for Left Side or
 * both side reflective)
 * 
 * @author Bankim Aghera
 *
 */
public class ReflectiveMirror {
	public static final String RIGHT = "R";
	public static final String LEFT = "L";

	/**
	 * Direction of Leaning (R for Right and L for Left)
	 */
	public String mLeaningDirection;

	/**
	 * true if right side is reflective
	 */
	public boolean mRightSide;

	/**
	 * true if left side is reflective
	 */
	public boolean mLeftSide;

	/**
	 * Constructs a new ReflectiveMirror with Direction of Leaning (R for Right and
	 * L for Left) and both side are reflective (2 way mirror), the input reflective
	 * side is not specified.
	 * 
	 * @param leaningDirection
	 *            - Direction of Leaning (R for Right and L for Left)
	 */
	public ReflectiveMirror(String leaningDirection) {
		if (!leaningDirection.equals(ReflectiveMirror.RIGHT) && !leaningDirection.equals(ReflectiveMirror.LEFT)) {
			throw new RuntimeException("The leaning '" + leaningDirection + "' direction of mirror is not supported.");
		}

		mLeaningDirection = leaningDirection;
		mRightSide = true;
		mLeftSide = true;
	}

	/**
	 * Constructs a new ReflectiveMirror with Direction of Leaning (R for Right and
	 * L for Left) and one of the side is reflective and it is specified.
	 * 
	 * @param leaningDirection
	 *            - mirror leaning direction (R for Right and L for Left)
	 * @param reflectiveSide
	 *            - reflective side of the mirror (R for Right Side or L for Left
	 *            Side or both side reflective)
	 */
	public ReflectiveMirror(String leaningDirection, String reflectiveSide) {
		this(leaningDirection);

		if (!reflectiveSide.equals(ReflectiveMirror.RIGHT) && !reflectiveSide.equals(ReflectiveMirror.LEFT)) {
			throw new RuntimeException("The '" + reflectiveSide + "' reflective side of a mirror is not supported.");
		}
		if (reflectiveSide.equals(ReflectiveMirror.RIGHT)) {
			mLeftSide = false;
		}
		if (reflectiveSide.equals(ReflectiveMirror.LEFT)) {
			mRightSide = false;
		}

	}
}