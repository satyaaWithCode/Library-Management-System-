

import React, { useState, useEffect } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
} from "@mui/material";

const BookForm = ({ open, onClose, onSubmit, initialData = {}, mode }) => {
  const [formData, setFormData] = useState({
    id: "",
    name: "",
    description: "",
    author: "",
    publisher: "",
  });

  useEffect(() => {
    if (initialData) {
      setFormData({
        id: initialData.id || "",
        name: initialData.name || "",
        description: initialData.description || "",
        author: initialData.author || "",
        publisher: initialData.publisher || "",
      });
    } else {
      setFormData({
        id: "",
        name: "",
        description: "",
        author: "",
        publisher: "",
      });
    }
  }, [initialData, open]); // Also reset when dialog opens

  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = () => {
    // Basic validation (optional)
    if (!formData.name || !formData.author) {
      alert("Please fill in required fields");
      return;
    }

    console.log("📘 Submitting book form data:", formData); // Debug log
    onSubmit(formData); // 🔥 send data to parent
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
      <DialogTitle>{mode === "edit" ? "Edit Book" : "Add Book"}</DialogTitle>
      <DialogContent className="flex flex-col gap-4">
        <TextField
          name="name"
          label="Book Name"
          value={formData.name}
          onChange={handleChange}
          required
          fullWidth
        />
        <TextField
          name="description"
          label="Description"
          value={formData.description}
          onChange={handleChange}
          fullWidth
        />
        <TextField
          name="author"
          label="Author"
          value={formData.author}
          onChange={handleChange}
          required
          fullWidth
        />
        <TextField
          name="publisher"
          label="Publisher"
          value={formData.publisher}
          onChange={handleChange}
          fullWidth
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cancel</Button>
        <Button variant="contained" onClick={handleSubmit}>
          {mode === "edit" ? "Update" : "Add"}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default BookForm;
