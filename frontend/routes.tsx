import {protectRoutes} from '@hilla/react-auth';
import TradeView from 'Frontend/views/trade/TradeView';
import LoginView from 'Frontend/views/login/LoginView.js';
import MainLayout from 'Frontend/views/MainLayout.js';
import {lazy} from 'react';
import {createBrowserRouter, RouteObject, useNavigate} from 'react-router-dom';
import RegisterView from "Frontend/views/signUp/RegisterView";
import WelcomeView from "Frontend/views/WelcomeView";

const TradingView = lazy(async () => import('Frontend/views/profile/profileView'));

export const routes = protectRoutes([
    {path: '/', element: <WelcomeView/>},
    {path: '/register', element: <RegisterView/>},
    {path: '/login', element: <LoginView/>},
    {
        element: <MainLayout/>,
        handle: {title: 'Main'},
        children: [
            {path: '/home', element: <TradeView/>, handle: {title: 'Enjoy Trading', requiresLogin: true}},
            {path: '/trade', element: <TradingView/>, handle: {title: 'Profile', requiresLogin: true}},
        ],
    },


]) as RouteObject[];

export default createBrowserRouter(routes);
