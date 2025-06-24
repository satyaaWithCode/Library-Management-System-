


import React, { useState } from "react";
import {
  Button,
  Menu,
  MenuItem,
  Typography,
  Divider,
  ListItemText,
} from "@mui/material";
import api from "../api";

export default function UserToggle() {
  const [users, setUsers] = useState([]);
  const [anchorEl, setAnchorEl] = useState(null);

  const handleOpen = async (event) => {
    setAnchorEl(event.currentTarget);
    if (users.length === 0) {
      try {
        const res = await api.get("/users");  //admin get all data of users
        setUsers(res.data.data ?? res.data);
      } catch (error) {
        console.error("Error loading users:", error);
      }
    }
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <div>
      <Button
        variant="outlined"
        size="small"
        className="bg-white"
        onClick={handleOpen}
      >
        Student Details
      </Button>

      <Menu
        anchorEl={anchorEl}
        open={Boolean(anchorEl)}
        onClose={handleClose}
        PaperProps={{
          style: {
            maxHeight: 300,
            width: "300px",
            overflowY: "auto",
          },
        }}
      >
        <Typography variant="h6" className="px-4 py-2">
          Registered Students ({users.length})
        </Typography>
        <Divider />
        {users.length === 0 ? (
          <MenuItem disabled>Loading...</MenuItem>
        ) : (
          users.map((u) => (
            <MenuItem key={u.id}>
              <ListItemText
                primary={`${u.name} (${u.role})`}
                secondary={u.email}
              />
            </MenuItem>
          ))
        )}
      </Menu>
    </div>
  );
}
