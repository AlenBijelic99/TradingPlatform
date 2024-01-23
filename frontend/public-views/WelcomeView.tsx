import {useNavigate} from 'react-router-dom';
import {Button} from "@hilla/react-components/Button.js";
import {VerticalLayout} from "@hilla/react-components/VerticalLayout";
import {AccordionHeading} from "@hilla/react-components/AccordionHeading";

export default function WelcomeView() {
    const navigate = useNavigate();

    const handleNavigateRegister = () => {
        navigate('/register');
    };

    const handleNavigateLogin = () => {
        navigate('/login');
    };

    return (
        <VerticalLayout theme="spacing padding" style={{alignItems: 'center'}}>
            <h1>Welcome to CryptO Trading</h1>
            <Button onClick={handleNavigateRegister}>Register</Button>
            <Button onClick={handleNavigateLogin}>Login</Button>
        </VerticalLayout>
    );
}
