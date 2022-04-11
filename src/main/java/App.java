import dao.ConnectionPool;
import dao.EntityDao;
import dao.EntityDaoException;
import dao.EntityDaoImpl;
import entity.Car;

import java.util.ResourceBundle;

/**
 * There is a Car entity, it has
 * - an identifier;
 * - a brand
 * - a color
 * - a price
 *
 * <p>The task is to create a project. Adding a table to the database must be
 * done through liquibase,
 * make tests using H2.</p>
 * Cover functionality with tests and make a report using the jacoco plugin.
 * Connect checkstyle to the project.
 * For the Person entity, make a DAO over each field, write your own annotation
 * MyColumn(name - the name of the column) with the name of the column,
 * write the annotation MyTable(name - the name of the table)
 * above the Car class.
 * Implement CRUD operations on Car using jdbc
 * <p>
 * - select
 * - update
 * - delete
 * - insert
 * <p>
 * Moreover, these operations should make a request to the database
 * using the annotations
 * MyColumn and MyTable (through reflection). i.e. if the user of
 * this API creates another entity, then
 * - select
 * - update
 * - delete
 * - insert
 * <p>should work without changing the internal logic</p>
 *
 * @author Sergey060890
 * @version 1.0
 */

public final class App {
    /**
     * Constant String name of database.
     */
    public static final String DATABASE = "database";
    /**
     * Constant id for update.
     */
    public static final long ID_FOR_UPDATE = 15L;
    /**
     * Constant id for delete.
     */
    public static final long ID_FOR_DELETE = 22L;
    /**
     * Just 1.
     */
    public static final int INCOMING_IDENTIFIER_1 = 1;
    /**
     * Just 2.
     */
    public static final int INCOMING_IDENTIFIER_2 = 2;
    /**
     * Just 3.
     */
    public static final int INCOMING_IDENTIFIER_3 = 3;
    /**
     * Just 4.
     */
    public static final int INCOMING_IDENTIFIER_4 = 4;
    /**
     * Just 5.
     */
    public static final int INCOMING_IDENTIFIER_5 = 5;
    /**
     * Price car 1.
     */
    public static final int PRICE_CAR_1 = 30000;
    /**
     * Price car 2.
     */
    public static final int PRICE_CAR_2 = 12000;
    /**
     * Price car 3.
     */
    public static final int PRICE_CAR_3 = 100000;
    /**
     * Price car 4.
     */
    public static final int PRICE_CAR_4 = 5000;
    /**
     * Price car 5.
     */
    public static final int PRICE_CAR_5 = 250000;


    private App() {
    }

    /**
     * Main class runner.
     *
     * @param args some args
     * @throws EntityDaoException Really rare exception
     */

    public static void main(final String[] args) throws EntityDaoException {
        ResourceBundle bundleMySql = ResourceBundle.getBundle(DATABASE);
        ConnectionPool connectionPool = new ConnectionPool(bundleMySql);
        EntityDao dao = new EntityDaoImpl(Car.class, connectionPool);

        Car car = new Car(INCOMING_IDENTIFIER_1, "AUDI",
                "GREY", PRICE_CAR_1);
        Car car1 = new Car(INCOMING_IDENTIFIER_2, "PEUGEOT",
                "BLACK", PRICE_CAR_2);
        Car car2 = new Car(INCOMING_IDENTIFIER_3, "MERCEDES",
                "WHITE", PRICE_CAR_3);
        Car car3 = new Car(INCOMING_IDENTIFIER_4, "CITROEN",
                "BLUE", PRICE_CAR_4);
        Car car4 = new Car(INCOMING_IDENTIFIER_5, "FERRARI",
                "RED", PRICE_CAR_5);
        dao.insert(car);
        dao.insert(car1);
        dao.insert(car3);
        dao.update(ID_FOR_UPDATE, car);
        dao.delete(ID_FOR_DELETE);
        dao.select();
    }
}
