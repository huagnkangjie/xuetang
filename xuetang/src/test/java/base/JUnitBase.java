package base;



import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value=SpringJUnit4ClassRunner.class)
 
@ContextConfiguration(locations = { "classpath:config/application-context.xml","classpath:config/redis-context.xml" })

public abstract class JUnitBase {

	
}
