import dao.ConnectionPool;
import dao.EntityDao;
import dao.EntityDaoException;
import dao.EntityDaoImpl;
import entity.Car;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static org.hamcrest.CoreMatchers.is;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest extends Assert {
    static ConnectionPool connectionPool;
    static EntityDao dao;
    public static final String DATABASE = "database";
    private static Map<Long, Object> map = new HashMap<>();//тестовая коллекция map
    private long finishKeyMap;//переменная хранящая номер последнего ID таблицы person
    private static Car car1;
    private static Car car2;
    private static Car car3;
    private static Car car4;
    private static Car car5;
    private static Object fakeEntity = new Object();


    public AppTest() throws Exception, EntityDaoException, SQLException {

    }


    @BeforeClass //метод будет выполняться в начале всех тестов
    public static void setUp() throws Exception {
        ResourceBundle bundleH2 = ResourceBundle.getBundle(DATABASE);
        connectionPool = new ConnectionPool(bundleH2);
        dao = new EntityDaoImpl(Car.class, connectionPool);
        assertTrue(connectionPool.getConnection() != null);
        car1 = new Car(2, "PEUGEOT", "BLACK", 12000);
        car2 = new Car(3, "MERCEDES", "WHITE", 100000);
        car3 = new Car(4, "CITROEN", "BLUE", 5000);
        car4 = null;
        car5 = new Car(4, "VOLKSWAGEN", "WHIE", 15000);
    }

    @Test
    public void A_insertDao() throws EntityDaoException {//проверка правильности добавления элементов (INSERT) с помощью сторонней Map
        dao.insert(car1);
        dao.insert(car2);
        dao.insert(car3);
        for (Long key : dao.select().keySet()//нахождение последнего использованного ключа
        ) {
            if (key.intValue() > finishKeyMap) {
                finishKeyMap = key.intValue();
            }
        }
        map.put(finishKeyMap - 2, car1);
        map.put(finishKeyMap - 1, car2);
        map.put(finishKeyMap, car3);
        assertEquals("Mistake! Element with ID: " + finishKeyMap + " not added!", map.get(finishKeyMap), dao.select().get(finishKeyMap));
        assertEquals("Mistake! Element with ID: " + (finishKeyMap - 1) + " not added!", map.get(finishKeyMap - 1), dao.select().get(finishKeyMap - 1));
        assertEquals("Mistake! Element with ID: " + (finishKeyMap - 2) + " not added!", map.get(finishKeyMap - 2), dao.select().get(finishKeyMap - 2));
    }

    @Test
    public void B_updateDao() throws EntityDaoException {//проверка метода UPDATE
        long updateElement = 1L;
        map.put(updateElement, car3);
        map.replace(updateElement, car3);
        try {
            dao.update(updateElement, car3);
        } catch (EntityDaoException e) {
            e.printStackTrace();
            System.out.println("Mistake! Attempt to replace the data of a non-existent string!");
        }
        Assert.assertEquals("Mistake! Data update operation failed!", map.get(updateElement), dao.select().get(updateElement));//Проверка на правильность работы UPDATE с помощью сторонней Map
    }

    @Test
    public void deleteDao() throws EntityDaoException {//проверка метода DELETE
        long deleteElement = 21L;
        try {
            dao.delete(deleteElement);
        } catch (EntityDaoException e) {
            e.printStackTrace();
            System.out.println("Mistake! Attempt to delete a non-existent row!");
        }
    }

    @Test
    public void selectDao() throws EntityDaoException {//проверка метода SELECT
        long elementSelect = 15L;
        Assert.assertNotNull("Mistake! Row not found!", dao.select().get(elementSelect));//Проверка на правильность работы выборки SELECT
    }

    @Test(expected = EntityDaoException.class)
    public void testUpdateException() throws EntityDaoException {//Проверка вызова исключения при обновлении данных
        Long updateExcemtion = 400L;
        dao.update(updateExcemtion, car5);
        Assert.fail("Mistake! Exception: " + EntityDaoException.class.getSimpleName() + " not thrown!");
    }

    @Test(expected = EntityDaoException.class)
    public void testDeleteException() throws EntityDaoException {//Проверка вызова исключения при удалении данных
        Long deleteExcemtion = 228L;
        dao.delete(deleteExcemtion);
        Assert.fail("Mistake! Exception: " + EntityDaoException.class.getSimpleName() + " not thrown!");
    }

    @Test(expected = NullPointerException.class)
    public void testInsertException() throws EntityDaoException {//Проверка вызова исключения при добавлении null данных
        dao.insert(car4);
        Assert.fail("Mistake! Exception: " + NullPointerException.class.getSimpleName() + " not thrown!");
    }


    @Test(expected = EntityDaoException.class)
    public void testEnityDaoImpl() throws EntityDaoException {//Проверка вызова исключения EntityDaoException при вызове конустрктора EntityDaoMySQLImpl
        class MyFakeClass {
            //создание фейкового класса для проверки работы EnityDaoException в конструкторе
        }
        EntityDao dao1 = new EntityDaoImpl(MyFakeClass.class, connectionPool);
        Assert.fail("Mistake! Exception: " + EntityDaoException.class.getSimpleName() + " not thrown!");
    }

    @Test(expected = EntityDaoException.class)
    public void testGetObjectParam() throws EntityDaoException {//Проверка вызова исключения EntityDaoException при вызове метода getObjectParam
        EntityDaoImpl entityDao = new EntityDaoImpl(Car.class, connectionPool);
        entityDao.update(1L, fakeEntity);//пытаемся обновить данные с помощью фейковой сущности
        Assert.fail("Mistake! Exception: " + EntityDaoException.class.getSimpleName() + " not thrown!");
    }

    @Test(expected = EntityDaoException.class)
    public void testCreateEntity() throws EntityDaoException {//Проверка вызова исключения EntityDaoException при вызове метода createEntity
        EntityDao dao2 = new EntityDaoImpl(Car.class, connectionPool);
        dao2.insert(fakeEntity);//пытаемся создать сущность, используя данные фейковой сущности
        Assert.fail("Mistake! Exception: " + EntityDaoException.class.getSimpleName() + " not thrown!");
    }

    @Test
    public void hashCodeEqualsCar() {//проверка hashCode и Equals Person
        Car actualCar = new Car(13, "LADA", "YELLOW", 6000);
        Car expectedCar = new Car(13, "LADA", "YELLOW", 6000);
        Car expectedCar2 = new Car(14, "LADA", "GREEN", 6000);
        //Проверка на равенство
        assertThat("Mistake! Objects are not equal!", actualCar, is(expectedCar));
    }

    @Test
    public void testToStringCar() {//проверка метода toString
        Car actualCar = new Car(20, "BENTLEY", "BLACK", 600000);
        String str = "CAR{identifier=20, brand='BENTLEY', color='BLACK', price='600000'}";
        Assert.assertEquals(str, actualCar.toString());
    }
}
