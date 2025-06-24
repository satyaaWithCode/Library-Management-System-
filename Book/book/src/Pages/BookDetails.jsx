


import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../api";
import { Button } from "@mui/material";
import ReactMarkdown from "react-markdown";
import rehypeSanitize from "rehype-sanitize"; //  For safe link rendering

const BookDetails = () => {
  const { bookId } = useParams();
  const [book, setBook] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    api.get(`/books/${bookId}`)
      .then((res) => setBook(res.data.data))
      .catch((err) => console.error("Error loading book:", err));
  }, [bookId]);

  if (!book) {
    return <div className="text-center mt-10 text-black">Loading book details...</div>;
  }

  return (
    <div className="p-10 min-h-screen bg-gray-100 text-black">
      <Button onClick={() => navigate(-1)} variant="outlined" className="mb-4">
        ← Back
      </Button>

      <h1 className="text-3xl font-bold mb-4">{book.name}</h1>
      <p className="mb-2"><strong>Author:</strong> {book.author}</p>
      <p className="mb-2"><strong>Publisher:</strong> {book.publisher}</p>
      <hr className="my-4" />

      <h2 className="text-xl font-semibold mb-2">📄 Book Documentation:</h2>
      <div className="prose prose-slate max-w-none">
        <ReactMarkdown rehypePlugins={[rehypeSanitize]}>
          {book.description}
        </ReactMarkdown>
      </div>
    </div>
  );
};

export default BookDetails;
