package producer;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.jms.*;

@Stateless
@Local
public class Producer {

	@Resource(name="java:jboss/DefaultJMSConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(name="java:/queue/test")
	private Destination destination;

	@Schedule(hour="*", minute = "*", second = "*/5", persistent = false)
	public void produceMessage() {
		try {
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer messageProducer = session.createProducer(destination);

			messageProducer.send(session.createTextMessage("Hello MDB"));
			System.out.println("--------------------------------------");
			connection.close();
			session.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
