


import React, { useEffect, useState } from "react";
import api from "../api";
import libraryBg from "../assets/library5.jpg";
import { useNavigate } from "react-router-dom";
import { Button } from "@mui/material";
import EmailDialog from "../Component/EmailDialog";
import NotificationListener from "../Component/NotificationListener"; 


const UserBooks = () => {
  const [books, setBooks] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [loading, setLoading] = useState(true);
  const [openDialog, setOpenDialog] = useState(false);
  const [emailData, setEmailData] = useState({
    name: "",
    from: "",
    to: "sbrata341@gmail.com",
    message: "",
  });
  const [attachment, setAttachment] = useState(null);

  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.clear();
    navigate("/", { replace: true });
  };

  // ⛳ Only handles the POST request — toast & close are handled in EmailDialog
  const handleSendEmail = async () => {
    const formData = new FormData();
    formData.append("name", emailData.name);
    formData.append("from", emailData.from);
    formData.append("to", emailData.to);
    formData.append("message", emailData.message);
    if (attachment) formData.append("file", attachment);

    const token = localStorage.getItem("token");

    await api.post("/email/send", formData, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "multipart/form-data",
      },
    });
  };

  useEffect(() => {
    api
      .get("/books")
      .then((res) => {
        setBooks(res.data.data);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Failed to fetch books:", error);
        setLoading(false);
      });
  }, []);

  const filteredBooks = books.filter((book) =>
    book.name.toLowerCase().includes(searchQuery.toLowerCase())
  );


  return (
    <div
      className="p-10 min-h-screen bg-cover bg-center bg-no-repeat"
      style={{
        backgroundImage: `url(${libraryBg})`,
        backgroundSize: "cover",
        backgroundRepeat: "no-repeat",
        backgroundPosition: "center",
      }}
    >
       <NotificationListener />
   
      {/* 🔐 Logout + 📧 Email Me */}
      <div className="absolute top-4 right-4 flex gap-2">
        <Button
          variant="contained"
          onClick={() => setOpenDialog(true)}
          className="bg-blue-500 text-white"
        >
          Email Me
        </Button>
        <button
  onClick={handleLogout}
  className="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-md shadow-md"
>
  Logout
</button>

      </div>

      <h1 className="text-3xl font-bold text-center text-zinc-300">
        📘 Welcome, Students 🧑‍🎓
      </h1>
      <p className="text-center mt-4 text-white">Here are your books 📚</p>

      {/* 🔍 Search */}
      <div className="flex justify-center mt-6">
        <input
          type="text"
          placeholder="Search by book name..."
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          className="w-full max-w-md px-4 py-2 rounded-lg shadow-md bg-white/20 placeholder-white text-white focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
      </div>

      {/* 📚 Book Cards */}
      {loading ? (
        <p className="text-center mt-10 text-lg text-gray-500">Loading books...</p>
      ) : filteredBooks.length === 0 ? (
        <p className="text-center mt-10 text-lg text-red-500">No books found.</p>
      ) : (
        <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-6 gap-4 mt-8">
          {filteredBooks.map((book) => (
            <div
              key={book.id}
              onClick={() => navigate(`/books/${book.id}`)}
              className="cursor-pointer bg-green-400 shadow-md rounded-md p-2 w-40 h-36 overflow-hidden flex flex-col justify-between hover:scale-105 transition-transform duration-200"
            >
              <h2 className="text-sm font-semibold text-gray-800 truncate">
                {book.name}
              </h2>
              <p className="text-xs text-gray-600 mt-1">
                <span className="font-semibold">Author:</span> {book.author}
              </p>
              <p className="text-xs text-gray-600">
                <span className="font-semibold">Publisher:</span> {book.publisher}
              </p>
              <p className="text-[10px] text-gray-500 mt-1 line-clamp-2">
                {book.description}
              </p>
            </div>
          ))}
        </div>
      )}

      {/* 📨 Email Dialog */}
      <EmailDialog
        open={openDialog}
        onClose={() => setOpenDialog(false)}
        emailData={emailData}
        setEmailData={setEmailData}
        onSend={handleSendEmail}
        attachment={attachment}
        setAttachment={setAttachment}
      />
    </div>
  );
};

export default UserBooks;
