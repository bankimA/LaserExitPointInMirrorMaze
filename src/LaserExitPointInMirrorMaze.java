import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 *
 * The Problem
 * --------------
 * You will be given a block of square rooms in an X by Y configuration, with a door in the center of every wall.  Some rooms will have a mirror in them at a 45 degree angle.
 * The mirrors may reflect off both sides (2-way mirrors) or reflect off one side and allow the beam to pass through from the other (1-way mirrors).
 * When the laser hits the reflective side of one of the mirrors, the beam will reflect off at a 90 degree angle.
 * Your challenge is to calculate the exit point of a laser shot into one of the open doors.
 * You need to provide the room it will be exiting through along with the orientation.
 * The definition file will be provided through command line parameters.
 * 
 * The Mirrors
 * ------------
 * There are two types of mirrors that may appear in definition file, 2-way and 1-way.
 * A 2-way mirror has a reflective surface on both sides.  So no matter which side a beam strikes the mirror on, it will reflect off at a 90 degree angle away from the mirror.
 * A 1-way mirror has a reflective surface on one side.  When a laser beam strikes the reflective side of the mirror, it will reflect off at a 90 degree angle away from the mirror.  If the laser beam strikes the non-reflective side, it will pass through the room as if the mirror was not there.
 * 
 * The Definition File
 * -------------------
 * The input file will be an ASCII text file with the following format:
 * The board size
 * -1
 * Mirror placements
 * -1
 * Laser entry room
 * -1
 * 
 * Description of each section of the definition file:
 * The board size
 * ---------------
 * The board size is provided in X,Y coordinates.
 * 
 * Mirror placements
 * -----------------
 * The mirror placement will be in X,Y coordinates indicating which room the mirror is located.  
 * It will be followed by an R or L indicating the direction the mirror is leaning (R for Right and L for Left).  
 * That will be followed by an R or L indicating the side of the mirror that is reflective, if it's a 1-way mirror (R for Right Side or L for Left Side) or
 * nothing, if both sides are reflective and it's a 2-way mirror.
 * 
 * Laser entry room
 * -----------------
 * The laser entry room is provided in X,Y coordinates followed by an H or V (H for Horizontal or V for Vertical) 
 * to indicated the laser orientation.
 * 
 * A Sample Text File
 * ------------------
 * 5,4
 * -1
 * 1,2RR
 * 3,2L
 * -1
 * 1,0V
 * -1
 * 
 * Output
 * -------
 * At a minimum, your application should print the following to the screen:
 * 1.	The dimensions of the board
 * 2.	The start position of the laser in the format (X, Y) and the orientation (H or V)
 * 3.	The exit point of the laser in the format (X, Y) and the orientation (H or V)
 *
 */
public class LaserExitPointInMirrorMaze {

	public static void main(String[] args) throws IOException {

		if (args.length == 0) {
			System.out.println("please give maze definition input file name and path.");
			return;
		}

		parseMazeDefinitionFile(args[0]);
	}

	private static void parseMazeDefinitionFile(String fileName) throws FileNotFoundException, IOException {
		int mazeRow = 0;
		int mazeCol = 0;
		ArrayList<String> mirrors = new ArrayList<String>();
		int laserStartRow = -1;
		int laserStartCol = -1;
		String laserOrientation = null;
		int inputCounter = 0;
		
		FileReader fin = null;
		
		try {
			fin = new FileReader(fileName);	
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
			return;
		}
		
		Scanner in = new Scanner(fin);

		// read the configuration for the mirror board and the laser
		while (in.hasNextLine()) {
			String nextLine = in.nextLine().trim();
			if (nextLine.startsWith("-1")) {
				inputCounter++;
				if (in.hasNext())
					nextLine = in.nextLine().trim();
			}
			
			// read maze size
			if (inputCounter == 0) {
				String mazeSize = nextLine;
				mazeCol = Integer.valueOf(mazeSize.trim().split(",")[0]);
				mazeRow = Integer.valueOf(mazeSize.trim().split(",")[1]);
			}
			
			// read mirror
			if (inputCounter == 1) {
				mirrors.add(nextLine);
			}

			// read laser
			if (inputCounter == 2) {
				String laser = nextLine;
				laserStartCol = Integer.valueOf(laser.substring(0, 1));
				laserStartRow = Integer.valueOf(laser.substring(2, 3));
				laserOrientation = new String(laser.substring(3));
			}
		}

		try {
			in.close();			
		} catch (Exception e) {
		}
		
		try {
			fin.close();			
		} catch (Exception e) {
		}
		
		ReflectiveMirror[][] totalMirrors = new ReflectiveMirror[mazeCol][mazeRow];
		for (String m : mirrors) {

			ReflectiveMirror mirror;
			if (m.length() > 4) {
				String d = new String(m.substring(3, 4));
				String s = new String(m.substring(4));
				mirror = new ReflectiveMirror(d, s);
			} else {
				String d = new String(m.substring(3, 4));
				mirror = new ReflectiveMirror(d);
			}
			int col = Integer.valueOf(m.substring(0, 1));
			int row = Integer.valueOf((m.substring(2, 3)));
			totalMirrors[col][row] = mirror;
		}

		findLaserPathInMaze(totalMirrors, laserStartCol, laserStartRow, laserOrientation);
	}

	/**
	 * This method will find the path of a laser in the mirror maze.
	 * 
	 * @param mirrors
	 *            - Total number of mirrors
	 * @param laserStartColumnNo
	 *            - start column position of laser
	 * @param laserStartRowNo
	 *            - start row position of laser
	 * @param laserOrientation
	 *            - orientation of laser (H or V)
	 */
	public static void findLaserPathInMaze(ReflectiveMirror[][] mirrors, int laserStartColumnNo, int laserStartRowNo,
			String laserOrientation) {
		// validate the input of the laser
		if (laserStartColumnNo < 0 || laserStartRowNo < 0 || laserStartColumnNo >= mirrors.length
				|| laserStartRowNo >= mirrors[0].length
				|| (!laserOrientation.equals(BlockPosition.ORIENTATION_HORIZONTAL)
						&& !laserOrientation.equals(BlockPosition.ORIENTATION_VERTICAL))) {
			System.out.println("incorrect input");
			return;
		}

		System.out.println("The size of board (X into Y): " + mirrors.length + " x " + mirrors[0].length);

		// track the path of the laser
		ArrayList<BlockPosition> path = new ArrayList<BlockPosition>();
		String direction = BlockPosition.MOVE_FORWARD; // "+": increase step; "-": decrease step
		path.add(new BlockPosition(laserStartColumnNo, laserStartRowNo, laserOrientation, direction));

		// if last position is out of board, it is finished.
		BlockPosition last = path.get(path.size() - 1);
		while ((last.mColumn >= 0 && last.mColumn < mirrors.length)
				&& (last.mRow >= 0 && last.mRow < mirrors[0].length)) {
			nextPosition(mirrors, path);
			last = path.get(path.size() - 1);
		}

		// print the path from start to finish.
		System.out.println("Following is the path of the laser in the maze: ");
		final int sizeMirrors = path.size() - 1;
		
		for (int i = 0; i < sizeMirrors; i++) {
			BlockPosition p = path.get(i);
			System.out.println(p);
		}

	}

	/**
	 * This method will calculate next block position of the laser
	 * 
	 * @param mirrors
	 * @param mazeBlocks
	 */
	public static void nextPosition(ReflectiveMirror[][] mirrors, ArrayList<BlockPosition> mazeBlocks) {
		BlockPosition prev = mazeBlocks.get(mazeBlocks.size() - 1);
		int prevCol = prev.mColumn;
		int prevRow = prev.mRow;
		String prevOrient = prev.mOrientation;
		String prevDirection = prev.mDirection;
		int nextCol = -1;
		int nextRow = -1;
		String nextOrient = prevOrient;
		String nextDirection = prevDirection;

		if (prevOrient.equals(BlockPosition.ORIENTATION_HORIZONTAL)) {
			nextCol = prevCol + ((prevDirection.equals(BlockPosition.MOVE_FORWARD)) ? 1 : -1);
			nextRow = prevRow;
		}
		if (prevOrient.equals(BlockPosition.ORIENTATION_VERTICAL)) {
			nextRow = prevRow + ((prevDirection.equals(BlockPosition.MOVE_FORWARD)) ? 1 : -1);
			nextCol = prevCol;
		}

		if ((nextCol >= 0 && nextCol < mirrors.length) && (nextRow >= 0 && nextRow < mirrors[0].length)) {

			ReflectiveMirror mirror = mirrors[nextCol][nextRow];
			if (mirror != null) {
				if (mirror.mLeaningDirection.equals(ReflectiveMirror.RIGHT)) {

					if (mirror.mRightSide) {
						if (prevOrient.equals(BlockPosition.ORIENTATION_VERTICAL)
								&& prevDirection.equals(BlockPosition.MOVE_FORWARD)) {
							nextOrient = BlockPosition.ORIENTATION_HORIZONTAL;
							nextDirection = BlockPosition.MOVE_FORWARD;

						}
						if (prevOrient.equals(BlockPosition.ORIENTATION_HORIZONTAL)
								&& prevDirection.equals(BlockPosition.MOVE_BACKWARD)) {
							nextOrient = BlockPosition.ORIENTATION_VERTICAL;
							nextDirection = BlockPosition.MOVE_BACKWARD;
						}
					}

					if (mirror.mLeftSide) {
						if (prevOrient.equals(BlockPosition.ORIENTATION_VERTICAL)
								&& prevDirection.equals(BlockPosition.MOVE_BACKWARD)) {
							nextOrient = BlockPosition.ORIENTATION_HORIZONTAL;
							nextDirection = BlockPosition.MOVE_BACKWARD;

						}
						if (prevOrient.equals(BlockPosition.ORIENTATION_HORIZONTAL)
								&& prevDirection.equals(BlockPosition.MOVE_FORWARD)) {
							nextOrient = BlockPosition.ORIENTATION_VERTICAL;
							nextDirection = BlockPosition.MOVE_FORWARD;
						}
					}
				}

				if (mirror.mLeaningDirection.equals(ReflectiveMirror.LEFT)) {
					if (mirror.mRightSide) {
						if (prevOrient.equals(BlockPosition.ORIENTATION_VERTICAL)
								&& prevDirection.equals(BlockPosition.MOVE_BACKWARD)) {
							nextOrient = BlockPosition.ORIENTATION_HORIZONTAL;
							nextDirection = BlockPosition.MOVE_FORWARD;

						}
						if (prevOrient.equals(BlockPosition.ORIENTATION_HORIZONTAL)
								&& prevDirection.equals(BlockPosition.MOVE_BACKWARD)) {
							nextOrient = BlockPosition.ORIENTATION_VERTICAL;
							nextDirection = BlockPosition.MOVE_FORWARD;
						}
					}

					if (mirror.mLeftSide) {
						if (prevOrient.equals(BlockPosition.ORIENTATION_VERTICAL)
								&& prevDirection.equals(BlockPosition.MOVE_FORWARD)) {
							nextOrient = BlockPosition.ORIENTATION_HORIZONTAL;
							nextDirection = BlockPosition.MOVE_BACKWARD;

						}
						if (prevOrient.equals(BlockPosition.ORIENTATION_HORIZONTAL)
								&& prevDirection.equals(BlockPosition.MOVE_FORWARD)) {
							nextOrient = BlockPosition.ORIENTATION_VERTICAL;
							nextDirection = BlockPosition.MOVE_BACKWARD;
						}
					}
				}
			}
		}

		BlockPosition next = new BlockPosition(nextCol, nextRow, nextOrient, nextDirection);
		for (BlockPosition p : mazeBlocks) {
			// check if the laser is trapped in the maze, path is handy for this.
			if (p.equals(next)) {
				throw new RuntimeException("The laser is trapped in the maze.");
			}
		}
		mazeBlocks.add(next);

	}
}