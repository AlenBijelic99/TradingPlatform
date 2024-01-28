import {useNavigate} from 'react-router-dom';
import {Button} from "@hilla/react-components/Button.js";
import {VerticalLayout} from "@hilla/react-components/VerticalLayout";

/**
 * The welcome view. This view is displayed when the user navigates to the / route.
 * It displays a welcome message and buttons to navigate to the login and register views.
 * @constructor
 * @Author Bijelic Alen & Bogale Tegest
 * @Date 28.01.2024
 */
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
