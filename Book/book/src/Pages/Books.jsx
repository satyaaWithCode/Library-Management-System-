
import React, { useEffect, useState } from "react";
import api from "../api";
import BookCard from "../Component/BookCard";
import BookForm from "../Component/BookForm";
import { Button, TextField, Menu, MenuItem } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import { useNavigate } from "react-router-dom";
import UserToggle from "../Component/UserToggle";
import libraryBg from "../assets/library5.jpg";




const Books = () => {
  const [books, setBooks] = useState([]);
  const [adminMode, setAdminMode] = useState(false);
  const [searchQuery, setSearchQuery] = useState("");
  const [anchorEl, setAnchorEl] = useState(null);
  const [selectedBook, setSelectedBook] = useState(null);
  const [openDialog, setOpenDialog] = useState(false);
  const [formMode, setFormMode] = useState("add");


  const navigate = useNavigate();

const handleLogout = () => {
  localStorage.clear();
  navigate("/", { replace: true });
};

  const fetchBooks = async () => {
    try {
      const response = await api.get("/books");
      setBooks(response.data.data);
    } catch (error) {
      console.error("Error fetching books:", error);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      await fetchBooks();
      const storedRole = localStorage.getItem("role");
      setAdminMode(storedRole && storedRole.toUpperCase().includes("ADMIN"));
    };

    fetchData();

    const handleVisibilityChange = () => {
      if (!document.hidden) {
        const role = localStorage.getItem("role");
        setAdminMode(role && role.toUpperCase().includes("ADMIN"));
      }
    };

    document.addEventListener("visibilitychange", handleVisibilityChange);
    return () => {
      document.removeEventListener("visibilitychange", handleVisibilityChange);
    };
  }, []);

  const handleOpenMenu = (event, book) => {
    setAnchorEl(event.currentTarget);
    setSelectedBook(book);
  };

  const handleCloseMenu = () => {
    setAnchorEl(null);
    setSelectedBook(null);
  };

  const handleAdd = () => {
    setFormMode("add");
    setSelectedBook(null);
    setOpenDialog(true);
  };

  const handleEdit = () => {
    setFormMode("edit");
    setOpenDialog(true);
  };

  const handleDelete = async () => {
    try {
      await api.delete(`/books/${selectedBook.id}`);
      fetchBooks();
    } catch (error) {
      console.error("Error deleting book:", error);
    } finally {
      handleCloseMenu();
    }
  };

  // const handleFormSubmit = async (formData) => {
  //   try {
  //     if (formMode === "add") {
  //       await api.post("/books", formData);
  //     } else {
  //       await api.put(`/books/${formData.id}`, formData);
  //     }
  //     fetchBooks();
  //     setOpenDialog(false);
  //   } catch (error) {
  //     console.error("Error saving book:", error);
  //   }
  // };

  // const filteredBooks = books.filter((book) =>
  //   book.name.toLowerCase().includes(searchQuery.toLowerCase())
  // );

  const handleFormSubmit = async (formData) => {
  try {
    console.log("Submitting form with data:", formData);

    if (formMode === "add") {
      await api.post("/books", formData);

      // Send full formData to match the expected @RequestBody Book
      await api.post("/kafka/books/notify", formData);
    } else {
      await api.put(`/books/${formData.id}`, formData);
    }

    fetchBooks();
    setOpenDialog(false);
  } catch (error) {
    console.error("Error saving book:", error);
    alert("Something went wrong while saving the book.");
  }
};


const filteredBooks = books.filter((book) =>
  book.name.toLowerCase().includes(searchQuery.toLowerCase())
);
  return (
    <div
      className="min-h-screen bg-cover bg-center bg-no-repeat"
      style={{ backgroundImage: `url(${libraryBg})` }}
    >
      
      <div className="min-h-screen bg-opacity-80 backdrop-blur-sm p-6">
        <h1 className="text-white text-4xl font-bold text-center mb-6">📚 Admin Books Library</h1>
    

{adminMode && (
  <div className="absolute top-4 right-4 flex space-x-3">
    <div className="mt-1">
      <UserToggle />
    </div>
    <button
  onClick={handleLogout}
  className="bg-red-600 hover:bg-red-700 text-white px-2 py-1 rounded-md shadow-md"
>
  Logout
</button>

  </div>
)}


{/* {adminMode && <UserToggle />}  */}

        <div className="flex justify-center mb-4">
          <TextField
            variant="outlined"
            placeholder="Search by book name..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="bg-white rounded w-full sm:w-[500px]"
            size="small"
          />
        </div>

        {adminMode && (
          <div className="mt-4 flex justify-center">
            <Button
              variant="outlined"
              startIcon={<AddIcon />}
              onClick={handleAdd}
              className="bg-white"
            >
              New Book

            </Button>
          </div>

          
        )}

        <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-6 gap-4 mt-8">
          {filteredBooks.map((book) => (
            <div
              key={book.id}
              className="bg-green-400 shadow-md rounded-md p-2 w-40 h-36 overflow-hidden flex flex-col justify-between hover:scale-105 transition-transform duration-200 relative"
            >
              <h2 className="text-sm font-semibold text-gray-800 truncate">{book.name}</h2>
              <p className="text-xs text-gray-600 mt-1">
                <span className="font-semibold">Author:</span> {book.author}
              </p>
              <p className="text-xs text-gray-600">
                <span className="font-semibold">Publisher:</span> {book.publisher}
              </p>
              <p className="text-[10px] text-gray-500 mt-1 line-clamp-2">{book.description}</p>

              {adminMode && (
                <button
                  onClick={(e) => handleOpenMenu(e, book)}
                  className="absolute top-1 right-1 text-xs text-blue-700 underline"
                >
                  •••
                </button>
              )}
            </div>
          ))}
        </div>

        <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={handleCloseMenu}>
          <MenuItem onClick={handleEdit}>Edit</MenuItem>
          <MenuItem onClick={handleDelete}>Delete</MenuItem>
        </Menu>

        <BookForm
          open={openDialog}
          onClose={() => setOpenDialog(false)}
          onSubmit={handleFormSubmit}
          initialData={selectedBook}
          mode={formMode}
        />
      </div>
    </div>
  );
};

export default Books;  

// ...........................................................  

