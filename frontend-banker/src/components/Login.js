import {useState} from 'react';
import {loginFields} from "../constants/formFields";
import Input from "./Input";
import FormAction from "./FormAction";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import Client from "../entities/Client"

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

    const response = await axios.get("http://localhost:8082/clients/getAllAccounts");
    const accounts = response.data.map((clientData) => {
      return new Client(
        clientData.id,
        clientData.clientId,
        clientData.creditCardNumber,
        clientData.amount
      );
    });


    const isClientExists = accounts.some((account) => account.clientId === clientID);

    if (isClientExists) {
      //login success
      alert("Login succeed");
      const client = accounts.find((account) => account.clientId === clientID)
      localStorage.setItem("client", client.clientId)
      navigate("/");
    } else {
      alert("Account not found")
      navigate("/signup")
    }
  }

  return (
    <form className="mt-8 space-y-6">
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
  )
}