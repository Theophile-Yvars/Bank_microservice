import {useState} from 'react';
import {loginFields} from "../constants/formFields";
import Input from "./Input";
import FormAction from "./FormAction";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import Client from "../entities/Client"
import "./styles/login.css"

const fields = loginFields;
let fieldsState = {};
fields.forEach(field => fieldsState[field.id] = '');

export default function Login() {
  const [loginState, setLoginState] = useState(fieldsState);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setLoginState({...loginState, [e.target.id]: e.target.value})
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    await authenticateUser(loginState);
  }

  //Handle Login API Integration here
  const authenticateUser = async (loginData) => {
    const clientID = parseInt(loginData["client-id"]);
    console.log(loginData)

    // Vérifier si clientID existe dans la liste des clients
    const response = await axios.get("http://localhost:8088/clients/clients/getAllAccounts");
    const isClientExists = response.data.some(clientData => clientData.clientId === clientID);

    if (isClientExists) {
      console.log(`Client ID ${clientID} exists in the list.`);
      //login success
      alert("Login succeed");
      localStorage.setItem("client", clientID)
      navigate("/");
    } else {
      console.log(`Client ID ${clientID} does not exist in the list.`);
      alert("Account not found")
      navigate("/signup")
    }
  }

  return (
      <div className="login">
        <div className="login-form">
          <form className=" space-y-6">
            <div className="-space-y-px">
              {
                fields.map(field =>
                  <Input
                    key={field.id}
                    handleChange={handleChange}
                    value={loginState[field.id]}
                    labelText={field.labelText}
                    labelFor={field.labelFor}
                    id={field.id}
                    name={field.name}
                    type={field.type}
                    isRequired={field.isRequired}
                    placeholder={field.placeholder}
                  />
                )
              }

            </div>

            <FormAction handleSubmit={handleSubmit} text="Login"/>


          </form>
        </div>
      </div>
  )
}