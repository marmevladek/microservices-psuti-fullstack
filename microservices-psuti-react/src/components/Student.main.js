import React, { useState, useEffect } from "react";
import { Navigate } from "react-router-dom";
import { useSelector } from "react-redux";

import userService from "../services/user.service";
import eventBus from "../common/EventBus";

const StudentMain = () => {
    const [currentFile, setCurrentFile] = useState(undefined);
    const [progress, setProgress] = useState(0);
    const [message, setMessage] = useState("")

    const selectFile = (event) => {
        setCurrentFile(event.target.files[0]);
        setProgress(0);
        setMessage("");
    }

    

    const {user: currentUser} = useSelector((state) => state.auth);

    if (!currentUser) {
        console.log("error occurred")
        return <Navigate to={"/auth/login"} />
    }

    const upload = () => {
        setProgress(0);

        userService.studentMain(currentFile, currentUser.uid,(event) => {
            setProgress(Math.round((100 * event.loaded) / event.total));
        })
    }

    

    return (
        <>
            <div className="container">
                <header className="jumbotron">
                    <br />
                    <h3>{currentUser.uid}</h3>
                    <br />
                    <h3>{currentUser.role}</h3>
                    <br />
                    <h3>{currentUser.token}</h3>
                    <br />
                    <div className="row">
        <div className="col-8">
          <label className="btn btn-default p-0">
            <input type="file" onChange={selectFile} />
          </label>
        </div>
        <div className="col-4">
          <button
            className="btn btn-success btn-sm"
            disabled={!currentFile}
            onClick={upload}
          >
            Upload
          </button>
        </div>
      </div>
      {currentFile && (
        <div className="progress my-3">
          <div
            className="progress-bar progress-bar-info"
            role="progressbar"
            aria-valuenow={progress}
            aria-valuemin="0"
            aria-valuemax="100"
            style={{ width: progress + "%" }}
          >
            {progress}%
          </div>
        </div>
      )}
      {message && (
        <div className="alert alert-secondary mt-3" role="alert">
          {message}
        </div>
      )}
                </header>
            </div>
        </>
    )

}

export default StudentMain;