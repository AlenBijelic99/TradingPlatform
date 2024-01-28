import {LoginI18n, LoginOverlay, LoginOverlayElement} from '@hilla/react-components/LoginOverlay.js';
import {useAuth} from 'Frontend/util/auth';
import {useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';

const loginI18n: LoginI18n = {
    ...new LoginOverlayElement().i18n,
    header: {title: 'Crypto-trading', description: 'Sign in to your account'},
};

/**
 * The login view. This view is displayed when the user navigates to the /login route.
 * It displays a login form and a link to the register view.
 * @constructor
 * @Author Bijelic Alen & Bogale Tegest
 * @Date 28.01.2024
 */
export default function LoginView() {
    const {state, login} = useAuth();
    const [hasError, setError] = useState<boolean>();
    const navigate = useNavigate();

    // If the user is already logged in, redirect to the home page
    if (state.user) {
        navigate('/home');
        return null;
    }

    return (
        <>
            <LoginOverlay
                opened autofocus
                noForgotPassword
                error={hasError}
                i18n={loginI18n}
                onLogin={async ({detail: {username, password}}) => {
                    const {error} = await login(username, password);
                    if (error) {
                        setError(true);

                    } else {
                        const url = '/home';
                        const path = new URL(url, document.baseURI).pathname;
                        navigate(path);
                    }
                }}

            >
                <Link slot="footer" to={'/register'}>Go to register</Link>
            </LoginOverlay>
        </>

    );
}
