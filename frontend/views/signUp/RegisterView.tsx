import {Notification} from '@hilla/react-components/Notification.js';
import {AutoForm} from '@hilla/react-crud';
import {UserService} from 'Frontend/generated/endpoints';
import UserDtoModel from "Frontend/generated/ch/heigvd/application/data/dto/UserDtoModel";
import {PasswordField} from "@hilla/react-components/PasswordField";
import {Link, useNavigate} from "react-router-dom";
import {useAuth} from "Frontend/util/auth";

export default function RegisterView() {
    const {state} = useAuth()
    const navigate = useNavigate();

    // If the user is already logged in, redirect to the home page
    if (state.user) {
        navigate('/home');
        return null;
    }

    const redirectToLogin = () => {
        navigate('/login');
    };

    const handleOnSubmitError = ({error}: { error: unknown }) => {
        const json = JSON.stringify(error);
        Notification.show('Error while submitting: ' + json, {theme: 'error'});
    };
    return (
        <div className="container">
            <section className="form-section">
                <h1>Register</h1>
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
                <Link to={"/login"}> Go to login page</Link>
            </section>
        </div>
    );
}
