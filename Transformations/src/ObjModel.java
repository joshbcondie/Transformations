import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TRIANGLES;
import static javax.media.opengl.GL2.GL_POLYGON;
import static javax.media.opengl.GL2GL3.GL_QUADS;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.media.opengl.GL2;

/**
 * A class to represent a 3D model loaded from a .obj file. It contains
 * vertices, texture coordinates, and faces.
 */
public class ObjModel {

	private List<Vector3f> vertices;
	private List<Vector3f> textureCoordinates;
	private List<Face> faces;

	/**
	 * Creates an ObjModel object from a .obj file
	 * 
	 * @param file
	 *            the file to read.
	 * @throws FileNotFoundException
	 *             if the file does not exist.
	 */
	public ObjModel(File file) throws FileNotFoundException {

		vertices = new ArrayList<>();
		textureCoordinates = new ArrayList<>();
		faces = new ArrayList<>();

		Scanner scanner = new Scanner(file);

		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split(" ");
			if (line[0].equals("v")) {
				float x = Float.parseFloat(line[1]);
				float y = Float.parseFloat(line[2]);
				float z = Float.parseFloat(line[3]);
				vertices.add(new Vector3f(x, y, z));
			} else if (line[0].equals("vt")) {
				float x = Float.parseFloat(line[1]);
				float y = Float.parseFloat(line[2]);
				textureCoordinates.add(new Vector3f(x, y));
			} else if (line[0].equals("f")) {
				Face face = new Face();
				for (int i = 1; i < line.length; i++) {
					String[] sections = line[i].split("/");
					if (sections.length >= 1) {
						int vertex = Integer.parseInt(sections[0]);
						face.addVertex(vertex);
					}
					if (sections.length >= 2) {
						int textureCoordinate = Integer.parseInt(sections[1]);
						face.addTextureCoordinate(textureCoordinate);
					}
				}
				faces.add(face);
			}
		}

		scanner.close();
	}

	/**
	 * Renders the model to an instance of {@link GL2#}
	 * 
	 * @param gl
	 *            the instance to which the model will be rendered.
	 */
	public void render(GL2 gl, Matrix matrix) {
		for (Face face : faces) {
			gl.glEnable(GL_TEXTURE_2D);
			if (face.getVertices().size() == 3) {
				gl.glBegin(GL_TRIANGLES);
			} else if (face.getVertices().size() == 4) {
				gl.glBegin(GL_QUADS);
			} else {
				gl.glBegin(GL_POLYGON);
			}

			for (int i = 0; i < face.getVertices().size(); i++) {
				if (textureCoordinates.size() > i) {
					Vector3f textureCoordinate = textureCoordinates.get(face
							.getTextureCoordinates().get(i) - 1);
					gl.glTexCoord2f(textureCoordinate.getX(),
							textureCoordinate.getY());
				}
				int vertexIndex = face.getVertices().get(i);
				Vector3f vertex = vertices.get(vertexIndex - 1);
				Vector3f transformed = matrix.multiply(new Matrix(vertex))
						.toVector3f();
				gl.glVertex3f(transformed.getX(), transformed.getY(),
						transformed.getZ());
			}

			gl.glEnd();
		}
	}
}
