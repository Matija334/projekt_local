"use client";
import { useState } from "react";
import { signInWithEmailAndPassword } from "firebase/auth";
import { auth } from "@/firebase";

const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const userCredential = await signInWithEmailAndPassword(auth, email, password);
            const user = userCredential.user;
            if (user && !user.emailVerified) {
                alert("Please verify your email before logging in.");
                return;
            }
            window.location.href = '/';
        } catch (error) {
            console.error("Error logging in:", error);
        }
    };

    return (
        <div className={"flex flex-col items-center justify-center min-h-screen"}>
            <div className={"flex flex-col items-center justify-center rounded-8 p-10 dark:bg-primary-dark"}>
                <img className={"w-48 h-auto rounded-8"} src={"/logo-scm.png"} alt={"Logo"}/>
                <h3 className={"text-xl font-semibold mb-5 dark:text-white dark:pt-6"}>Log in with Email</h3>
                <form onSubmit={handleLogin} className={"flex flex-col items-center justify-center"}>
                    <label className="input input-bordered flex items-center gap-2 mb-2 text-secondary-dark dark:bg-white">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor"
                             className="w-4 h-4 opacity-70">
                            <path
                                d="M2.5 3A1.5 1.5 0 0 0 1 4.5v.793c.026.009.051.02.076.032L7.674 8.51c.206.1.446.1.652 0l6.598-3.185A.755.755 0 0 1 15 5.293V4.5A1.5 1.5 0 0 0 13.5 3h-11Z"/>
                            <path
                                d="M15 6.954 8.978 9.86a2.25 2.25 0 0 1-1.956 0L1 6.954V11.5A1.5 1.5 0 0 0 2.5 13h11a1.5 1.5 0 0 0 1.5-1.5V6.954Z"/>
                        </svg>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="Email"
                            required
                        />
                    </label>
                    <label className="input input-bordered flex items-center gap-2 mb-4 text-secondary-dark dark:bg-white">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor"
                             className="w-4 h-4 opacity-70">
                            <path fillRule="evenodd"
                                  d="M14 6a4 4 0 0 1-4.899 3.899l-1.955 1.955a.5.5 0 0 1-.353.146H5v1.5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1-.5-.5v-2.293a.5.5 0 0 1 .146-.353l3.955-3.955A4 4 0 1 1 14 6Zm-4-2a.75.75 0 0 0 0 1.5.5.5 0 0 1 .5.5.75.75 0 0 0 1.5 0 2 2 0 0 0-2-2Z"
                                  clipRule="evenodd"/>
                        </svg>
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Password"
                            required
                        />
                    </label>
                    <button type="submit"
                            className={"btn mt-4 btn-sm bg-primary-light border-0 text-white w-20 dark:hover:bg-primary-light rounded-8 font-semibold hover:scale-105 transition hover:bg-primary-dark"}>Log
                        in
                    </button>
                </form>
                <a href={"/register"} className={"mt-5 link text-primary-light dark:text-white text-xs"}>Do not have an account?
                    Register here!</a>
            </div>

        </div>

    );
};

export default Login;
