package club.broking.cdn.setup;

import club.broking.cdn.servlets.add.product.AddProductServlet;
import club.broking.cdn.servlets.admin.AdminServlet;
import club.broking.cdn.servlets.delete.product.DeleteProductServlet;
import club.broking.cdn.servlets.login.LoginServlet;
import club.broking.cdn.servlets.order.OrderServlet;
import club.broking.cdn.servlets.orders.OrdersServlet;
import club.broking.cdn.servlets.products.ProductsServlet;
import club.broking.cdn.servlets.register.RegisterServlet;
import club.broking.cdn.servlets.delete.user.DeleteUserServlet;
import club.broking.cdn.servlets.shopping.cart.ShoppingCartServlet;
import club.broking.cdn.servlets.shopping.cart.add.ShoppingCartAddServlet;
import club.broking.cdn.servlets.shopping.cart.delete.ShoppingCartDeleteServlet;
import club.broking.cdn.servlets.users.UsersServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Jetty {

    public void setup() {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);

        connector.setPort(9000);
        server.setConnectors(new Connector[]{ connector });

        ServletContextHandler context = new ServletContextHandler();

        context.setContextPath("/");

        /* Default Servlet */
        ServletHolder holder = context.addServlet(DefaultServlet.class, "/");

        holder.setInitParameter("resourceBase", "./public");
        holder.setInitParameter("dirAllowed", "false");
        /* Default Servlet */

        context.addServlet(RegisterServlet.class, "/api/register");
        context.addServlet(LoginServlet.class, "/api/login");
        context.addServlet(UsersServlet.class, "/api/users");
        context.addServlet(AdminServlet.class, "/api/admin");
        context.addServlet(DeleteUserServlet.class, "/api/delete/user");
        context.addServlet(AddProductServlet.class, "/api/add/product");
        context.addServlet(ProductsServlet.class, "/api/products");
        context.addServlet(DeleteProductServlet.class, "/api/delete/product");
        context.addServlet(ShoppingCartServlet.class, "/api/shopping/cart");
        context.addServlet(ShoppingCartAddServlet.class, "/api/shopping/cart/add");
        context.addServlet(ShoppingCartDeleteServlet.class, "/api/shopping/cart/delete");
        context.addServlet(OrdersServlet.class, "/api/orders");
        context.addServlet(OrderServlet.class, "/api/order");

        /* Error Handler */
        ErrorPageErrorHandler errorHandler = new ErrorPageErrorHandler();

        errorHandler.addErrorPage(404, "/");
        context.setErrorHandler(errorHandler);
        /* Error Handler */

        server.setHandler(context);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
