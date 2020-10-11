package consumers;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(name="ConsumerFirst",
	activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/ExpiryQueue")
	})
public class ConsumerFirst implements MessageListener {

	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage)	message;
		try {
			System.out.println(textMessage.getText() + this.getClass().toString());
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
