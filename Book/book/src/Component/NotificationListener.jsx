import { useEffect } from "react";
import { toast } from "react-toastify";

const NotificationListener = () => {
  useEffect(() => {
    // const socket = new WebSocket("ws://localhost:8081/ws/notifications");
    const token = localStorage.getItem("token");
    const socket = new WebSocket(`ws://localhost:8081/ws/notifications?token=${token}`);


    socket.onopen = () => {
      console.log(" WebSocket connected"); //connected websocket here 
    };

    socket.onmessage = (event) => {
      console.log("📨 Raw Notification:", event.data); //notification going from here to user 

      try {
        const data = JSON.parse(event.data); // Expecting JSON like { icon, message }
        const icon = data.icon || "📢";
        const message = data.message || event.data;

        toast.info(`${icon} ${message}`, {
          position: "top-right",
          autoClose: 5000,
        });
      } catch (err) {
        // Fallback for plain string messages
        toast.info(event.data, {
          position: "top-right",
          autoClose: 5000,
        });
      }
    };

    socket.onerror = (error) => {
      console.error("❌ WebSocket error:", error);
    };

    socket.onclose = () => {
      console.warn("⚠️ WebSocket disconnected");
    };

    return () => {
      socket.close();
    };
  }, []);

  return null;
};

export default NotificationListener;
