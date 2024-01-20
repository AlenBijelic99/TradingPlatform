import {Notification} from '@hilla/react-components/Notification.js';
import {AutoForm} from '@hilla/react-crud';
import {UserService} from 'Frontend/generated/endpoints.js';
import UserDtoModel from "Frontend/generated/ch/heigvd/application/data/dto/UserDtoModel";
import {PasswordField} from "@hilla/react-components/PasswordField";
import {useNavigate} from "react-router-dom";
import {Button} from "@hilla/react-components/Button.js";

export default function RegisterView() {

    const navigate = useNavigate();

    const redirectToLogin = () => {
        navigate('/login');
    };
    const redirectToWelcome = () => {
        navigate('/');
    };

    const handleOnSubmitError = ({error}: { error: unknown }) => {
        const json = JSON.stringify(error);
        Notification.show('Error while submitting: ' + json, {theme: 'error'});
    };
    return (
        <>
        <section>
            <AutoForm
                service={UserService}
                model={UserDtoModel}
                fieldOptions={{
                    password: {
                        renderer: ({field}) => <PasswordField {...field} label="Password"/>
                    },
                }}
                onSubmitSuccess={redirectToLogin}
                onSubmitError={handleOnSubmitError}
            />
            <button onClick={redirectToWelcome}> Go back to Home page</button>
        </section>
        </>
    );
}
