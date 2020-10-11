package producer;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.jms.*;

@Stateless
@Local
public class Producer {

	@Resource(mappedName="java:/JmsXA")
	private ConnectionFactory connectionFactory;

	@Resource(lookup="java:/jms/queue/ExpiryQueue")
	private Queue queue;

	private Connection connection;

	@Schedule(hour="*", minute = "*", second = "*/5", persistent = false)
	public void produceMessage() {
		try {
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer messageProducer = session.createProducer(queue);

			connection.start();

			messageProducer.send(session.createTextMessage("Hello MDB"));
			System.out.println("--------------------------------------");
			connection.close();
			session.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
