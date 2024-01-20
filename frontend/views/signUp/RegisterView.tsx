import {Notification} from '@hilla/react-components/Notification.js';
import {AutoForm} from '@hilla/react-crud';
import {UserService} from 'Frontend/generated/endpoints.js';
import UserDtoModel from "Frontend/generated/ch/heigvd/application/data/dto/UserDtoModel";
import { PasswordField } from "@hilla/react-components/PasswordField";

interface SignUpViewProps {
    onSignUpSuccess: () => void;
}
export default function SignUpView({onSignUpSuccess}:SignUpViewProps) {

    const handleOnSuccess = () => {
        onSignUpSuccess();
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
                    onSubmitSuccess={handleOnSuccess}
                    onSubmitError={handleOnSubmitError}
                />
            </section>
        </>
    );
}
