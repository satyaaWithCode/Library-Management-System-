

import React from "react";
import { IconButton, Tooltip } from "@mui/material";
import MoreVertIcon from "@mui/icons-material/MoreVert";

const BookCard = ({ book, admin, onMenuOpen }) => {
  return (
    <div className="bg-green-800 bg-opacity-90 rounded-xl shadow-md px-1 py-1 flex items-center justify-between hover:shadow-lg transition duration-300">
      <div className="flex items-center space-x-3">
        <span className="text-2xl">📚</span>
        <div>
          <h2 className="text-lg font-semibold capitalize">{book.name}</h2>
          <p className="text-sm text-gray-600">by {book.author}</p>
        </div>
      </div>
      {admin && (
        
        <Tooltip title="Actions">
          <IconButton onClick={(e) => onMenuOpen(e, book)}>
            <MoreVertIcon />
          </IconButton>
        </Tooltip>
      )}
    </div>
  );
};

export default BookCard;
