

import React from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
} from "@mui/material";
import { toast } from "react-toastify";

const EmailDialog = ({
  open,
  onClose,
  emailData,
  setEmailData,
  onSend,
  attachment,
  setAttachment,
}) => {
  const handleChange = (e) => {
    const { name, value } = e.target;
    setEmailData((prev) => ({ ...prev, [name]: value }));
  };

  const handleFileChange = (e) => {
    setAttachment(e.target.files[0]);
  };


const handleSendClick = async () => {
  onClose(); // ✅ Close dialog right away

  const toastId = toast.loading("📤 Sending email..."); // Show loading toast immediately

  try {
    await onSend(); // Wait for API to finish
    toast.update(toastId, {
      render: " Email sent successfully!",
      type: "success",
      isLoading: false,
      autoClose: 3000,
    });
  } catch (err) {
    toast.update(toastId, {
      render: "❌ Failed to send email.",
      type: "error",
      isLoading: false,
      autoClose: 3000,
    });
  }
};


  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>📨 Email Me</DialogTitle>
      <DialogContent sx={{ display: "flex", flexDirection: "column", gap: 2, mt: 1 }}>
        <TextField
          label="Your Name"
          name="name"
          value={emailData.name}
          onChange={handleChange}
          fullWidth
        />
        <TextField
          label="Your Email"
          name="from"
          value={emailData.from}
          onChange={handleChange}
          fullWidth
        />
        <TextField
          label="To (Receiver Email)"
          name="to"
          value={emailData.to}
          onChange={handleChange}
          fullWidth
        />
        <TextField
          label="Message"
          name="message"
          value={emailData.message}
          onChange={handleChange}
          multiline
          rows={4}
          fullWidth
        />
        <input
          type="file"
          onChange={handleFileChange}
          accept=".pdf,.doc,.docx,.jpg,.png"
          style={{ marginTop: "10px" }}
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cancel</Button>
        <Button variant="contained" onClick={handleSendClick}>
          Send
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default EmailDialog;
