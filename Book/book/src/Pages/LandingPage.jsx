

import { useNavigate } from "react-router-dom";
import libVideo from "../assets/lib.mp4"; // Make sure lib.mp4 is in src/assets

export default function LandingPage() {
  const navigate = useNavigate();

  return (
    <div className="relative min-h-screen w-full flex flex-col items-center justify-center text-white overflow-hidden">

      {/* 🎥 Background Video */}
      <video
        autoPlay
        loop
        muted
        playsInline
        className="absolute top-0 left-0 w-full h-full object-cover z-0"
      >
        <source src={libVideo} type="video/mp4" />
        Your browser does not support the video tag.
      </video>

      {/* 🌓 Optional Overlay (remove if you want full brightness) */}
      {/* <div className="absolute inset-0 bg-black bg-opacity-50 z-10" /> */}

      {/* 🌟 Foreground Content */}
      <div className="relative z-20 text-center px-4">
        <h1 className="text-5xl font-bold mb-4 drop-shadow-lg">Keep Silence</h1>
        <h2 className="text-4xl font-bold mb-6 drop-shadow-lg">
          Welcome to Our Books Library 📚
        </h2>
        <button
          onClick={() => navigate("/auth")}
          className="bg-white text-black px-8 py-3 rounded-lg text-lg font-semibold hover:bg-gray-200 transition duration-300 shadow-md"
        >
          Get Started
        </button>
      </div>
    </div>
  );
}
