const loginFields = [
  {
    labelText: "client ID",
    labelFor: "client-id",
    id: "client-id",
    name: "id",
    type: "text",
    autoComplete: "id",
    isRequired: true,
    placeholder: "Client ID"
  }
]

const signupFields = [
  {
    labelText: "first name",
    labelFor: "first-name",
    id: "first-name",
    name: "fisrt name",
    type: "text",
    autoComplete: "name",
    isRequired: true,
    placeholder: "Fisrt Name"
  },
  {
    labelText: "last name",
    labelFor: "last-name",
    id: "last-name",
    name: "last name",
    type: "text",
    autoComplete: "name",
    isRequired: true,
    placeholder: "Last Name"
  }
]

export {loginFields, signupFields}