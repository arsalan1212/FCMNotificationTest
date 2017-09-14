const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.NotificationPush = functions.database.ref('/message/{user_id}').onWrite(event => {
	
	var objectVal = event.data.val();
	console.log("Firebase Push Notification");
	
	var payload = {
		
	notification:{
		title:"FCM Notificaton",
		message:"New message"
	},
	data:{
		title:objectVal.title,
		message:objectVal.message
	}
	};
	
	const options={
		priority:"high",
		openTime:60 *60 *24
	};
	
	return admin.messaging().sendToTopic("notifications",payload,options);
	
	
});