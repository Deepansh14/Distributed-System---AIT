package myapp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/ParkingService")
public class ParkingService {

	@GET
	@Path("/items")
	@Produces(MediaType.APPLICATION_XML)
	public List<ParkingModel> getUsers() throws ClassNotFoundException, SQLException {
		return ParkingCRUD.getAllParkedCarsInformation();
	}

	@GET
	@Path("/items/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public ParkingModel getUser(@PathParam("id") int id) throws ClassNotFoundException, SQLException {
		return ParkingCRUD.getParkedCarInfo(id);
	}

//
	@POST
	@Path("/insertitems")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public int createUser(@FormParam("name") String name, @FormParam("numb") String numb,
			@FormParam("area") String area, @Context HttpServletResponse servletResponse)
			throws ClassNotFoundException, SQLException {
		return ParkingCRUD.parkCar(name, numb, area);
	}

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/{carthree}")
	public String postThreeCar(@FormParam("name") String name, @FormParam("carnumber") String carnumb,
			@FormParam("area") String area, @Context HttpServletResponse httpServletResponse)
			throws ClassNotFoundException {
		return ParkingCRUD.postThreeCar(name, carnumb, area);

	}

	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/{userID}")
	public void putcar(@PathParam("userID") String id, @FormParam("name") String name, @FormParam("numb") String numb,
			@FormParam("area") String area, @Context HttpServletResponse servletResponse) {
		ParkingCRUD.updateParkedCarInfo(id, name, numb, area);
	}

	@DELETE
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{userID}")
	public int deleteUserbyID(@PathParam("userID") String id) throws ClassNotFoundException, SQLException {
		return ParkingCRUD.deletebyID(Integer.parseInt(id));
	}

	@DELETE
	@Produces(MediaType.APPLICATION_XML)
	public int deleteUser() throws ClassNotFoundException, SQLException {
		return ParkingCRUD.deleteall();
	}

}
