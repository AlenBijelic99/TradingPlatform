import {LoginI18n, LoginOverlay, LoginOverlayElement} from '@hilla/react-components/LoginOverlay.js';
import {useAuth} from 'Frontend/util/auth.js';
import {useState} from 'react';
import {useNavigate} from 'react-router-dom';

const loginI18n: LoginI18n = {
    ...new LoginOverlayElement().i18n,
    header: {title: 'Crypto-trading', description: 'Sign in to your account'},
};

export default function LoginView() {
    const {login} = useAuth();
    const [hasError, setError] = useState<boolean>();
    const navigate = useNavigate();

    return (
        <LoginOverlay
            opened
            error={hasError}
            noForgotPassword
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
        />
    );
}
