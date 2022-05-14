package relations_example.one_to_many_bi;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import relations_example.one_to_many_bi.entity.Department;
import relations_example.one_to_many_bi.entity.Employee;

public class one_to_many_bi_example {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Department.class)
                .buildSessionFactory();

        Session session = null;

        try {
            session = factory.getCurrentSession();

            // Добавление работников в департамент
            Department dep = new Department("IT", 300, 1200);
            Employee emp1 = new Employee("John", "Deer", 500);
            Employee emp2 = new Employee("Jack", "Smith", 700);

            dep.addEmployeeToDepartment(emp1);
            dep.addEmployeeToDepartment(emp2);

            session.beginTransaction();
            session.save(dep);

            session.getTransaction().commit();


            // Получение объектов из БД
            session.beginTransaction();

            Department department = session.get(Department.class, 1);
            System.out.println(department);
            System.out.println(department.getEmps());

            Employee employee = session.get(Employee.class, 1);
            System.out.println(employee);
            System.out.println(employee.getDepartment());

            session.getTransaction().commit();


            // Удаление из БД
            session.beginTransaction();

            Employee employee1 = session.get(Employee.class, 1);
            session.delete(employee1);                                          // Тип cascade влияет на результат удаления

            session.getTransaction().commit();

        } finally {
            session.close();
            factory.close();
        }
    }
}
