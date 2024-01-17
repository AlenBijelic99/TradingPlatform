import { Button } from '@hilla/react-components/Button.js';
import { Notification } from '@hilla/react-components/Notification.js';
import { TextField } from '@hilla/react-components/TextField.js';
import {CryptoCurrencyService, HelloWorldService} from 'Frontend/generated/endpoints.js';
import { useState } from 'react';

export default function HelloWorldView() {
    const [name, setName] = useState('');

    return (
        <>
            <section className="flex p-m gap-m items-end">
                <TextField
                    label="Your name"
                    onValueChanged={(e) => {
                        setName(e.detail.value);
                    }}
                />
                <Button
                    onClick={async () => {
                        const serverResponse = await HelloWorldService.sayHello(name);
                        Notification.show(serverResponse);
                    }}
                >
                    Say hello
                </Button>


                <Button
                    onClick={async () => {
                        const btcPrice = await CryptoCurrencyService.getCryptoWithLastPrice('Bitcoin')
                        Notification.show(btcPrice.name);
                    }}
                >
                    Get Currency
                </Button>
            </section>
        </>
    );
}
