


import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "../api";
import { Eye, EyeOff } from "lucide-react";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import libraryBg from "../assets/library4.jpg";

export default function AuthPage() {
  const [isLogin, setIsLogin] = useState(true);
  const [showPassword, setShowPassword] = useState(false);
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("ROLE_USER");

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const endpoint = isLogin ? "/auth/login" : "/auth/register";
    const payload = isLogin
      ? { email, password }
      : { name, email, password, role };

    try {
      const res = await axios.post(endpoint, payload);

      if (isLogin) {
        const token = res.data.token;
        const userRole = res.data.role;

        localStorage.setItem("token", token);
        localStorage.setItem("role", userRole);

        toast.success(" Login successful!", { position: "top-center" });

        // Navigate based on role
        if (userRole === "ROLE_ADMIN") {
          navigate("/books");
        } else {
          navigate("/user/books");
        }
      } else {
        toast.success(" Registration successful! Please login.", {
          position: "top-center",
        });
        setIsLogin(true);
        setName("");
        setEmail("");
        setPassword("");
      }
    } catch (err) {
      const status = err?.response?.status;

      if (isLogin) {
        toast.error("❌ Invalid username or password", {
          position: "top-center",
        });
      } else {
        toast.error(
          "❌ Registration failed: " +
            (err?.response?.data?.message || "Unknown error"),
          { position: "top-center" }
        );
      }
    }
  };

  return (
    <div
      className="h-screen bg-cover bg-center flex items-center justify-center"
      style={{ backgroundImage: `url(${libraryBg})` }}
    >
      <form
        onSubmit={handleSubmit}
        className="backdrop-blur-md bg-white/20 border border-white/30 shadow-lg rounded-xl w-96 px-8 py-10 space-y-5 text-white"
      >
        <h2 className="text-3xl font-bold text-center drop-shadow-md">
          {isLogin ? "Login" : "Register"}
        </h2>

        {!isLogin && (
          <>
            <input
              type="text"
              placeholder="Name"
              className="w-full p-2 rounded bg-white/10 border border-white/30 text-white placeholder-white/70 focus:outline-none focus:ring-2 focus:ring-blue-500"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
            <select
              className="w-full p-2 rounded bg-white/10 border border-white/30 text-white placeholder-white/70 focus:outline-none focus:ring-2 focus:ring-blue-500"
              value={role}
              onChange={(e) => setRole(e.target.value)}
              required
            >
              <option value="ROLE_USER">User</option>
            </select>
          </>
        )}

        <input
          type="email"
          placeholder="Email"
          className="w-full p-2 rounded bg-white/10 border border-white/30 text-white placeholder-white/70 focus:outline-none focus:ring-2 focus:ring-blue-500"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />

        <div className="relative">
          <input
            type={showPassword ? "text" : "password"}
            placeholder="Password"
            className="w-full p-2 pr-10 rounded bg-white/10 border border-white/30 text-white placeholder-white/70 focus:outline-none focus:ring-2 focus:ring-blue-500"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <span
            className="absolute right-3 top-2.5 cursor-pointer text-white/70"
            onClick={() => setShowPassword(!showPassword)}
          >
            {showPassword ? <EyeOff size={20} /> : <Eye size={20} />}
          </span>
        </div>

        <button
          type="submit"
          className="w-full bg-blue-600 hover:bg-blue-700 transition text-white font-semibold py-2 rounded-lg shadow-md"
        >
          {isLogin ? "Login" : "Register"}
        </button>

        <p
          className="text-sm text-center text-blue-200 hover:text-blue-400 cursor-pointer transition"
          onClick={() => setIsLogin(!isLogin)}
        >
          {isLogin
            ? "Don't have an account? Register"
            : "Already have an account? Login"}
        </p>
      </form>
    </div>
  );
}
