package com.jt.text.rabbitmq;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class TestSimple {
	
	//初始化链接
	private Connection connection;
	
	private String queueName = "simple";
	
	/**
	 * IP:端口号
	 * 用户名/密码/虚拟机主机
	 * @throws IOException 
	 */
	@Before
	public void before() throws IOException{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.126.172");
		factory.setPort(5672);
		factory.setUsername("jtadmin");
		factory.setPassword("jtadmin");
		factory.setVirtualHost("/jt");
		connection = factory.newConnection();
	}
	
	@Test
	public void test01(){
		System.out.println("获取链接"+connection);
	}
	
	
	/**
	 * 实现消息的写入
	 * 步骤:
	 * 	1.定义Channel对象  用来管理队列
	 *  2.定义消息
	 *  3.定义队列
	 *  4.发送消息
	 * @throws IOException 
	 */
	@Test
	public void provider() throws IOException{
		//1.定义通道
		Channel channel  = connection.createChannel();
		
		//2.定义消息
		String msg = "我是简单模式";
		/*
		 * 3.定义队列
		 * 参数介绍:
		 * 	1.queue   队列名称
		 *  2.durable 队列是否持久化   true/false
		 *  3.exclusive 队列是否提供者独有  false
		 *  4.autoDelete 当消息处理完成后.是否自动删除队列 false
		 *  5.arguments 是否需要传递多余的参数 null
		 */
		channel.queueDeclare
		(queueName, false, false, false, null);
		
		/**
		 * 4.将消息写入队列
		 * 参数介绍
		 * 1.exchange  交换机  将消息发送到不同的队列中 ""
		 * 2.routingKey  消息发送的标识符  ""
		 * 3.props 其他的配置文件
		 * 4.消息的二进制数组
		 */
		channel.basicPublish
		("", queueName, null, msg.getBytes());
		channel.close();
		connection.close();
		System.out.println("消息队列发送成功!!!!!!");
		
	}
	
	/**
	 * 编辑消费者
	 * 1.定义Channel
	 * 2.定义队列
	 * 3.定义消费者
	 * 4.将队列与消费者进行绑定.
	 * 通过循环获取队列的消息 回复ack
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ConsumerCancelledException 
	 * @throws ShutdownSignalException 
	 */
	@Test
	public void consumer() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException{
		Channel channel = connection.createChannel();
		
		channel.queueDeclare
		(queueName, false, false, false, null);
		
		//定义消费者
		QueueingConsumer consumer = 
				new QueueingConsumer(channel);
		
		//消费者和队列进行绑定
		channel.basicConsume(queueName, true, consumer);
		
		while (true) {
			byte[] message = consumer.nextDelivery().getBody();
			String msg = new String(message);
			System.out.println(msg);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
