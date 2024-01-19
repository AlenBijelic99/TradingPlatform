import { Button } from '@hilla/react-components/Button.js';
import { Notification } from '@hilla/react-components/Notification.js';
import { TextField } from '@hilla/react-components/TextField.js';
import {Grid} from "@hilla/react-components/Grid";
import {GridColumn} from "@hilla/react-components/GridColumn";
import { AutoForm } from '@hilla/react-crud';
import {CryptoCurrencyService, UserService} from 'Frontend/generated/endpoints.js';
import {useEffect, useState} from 'react';
import CryptoCurrencyRecord
    from "Frontend/generated/ch/heigvd/application/services/CryptoCurrencyService/CryptoCurrencyRecord";
import UserDtoModel from "Frontend/generated/ch/heigvd/application/data/dto/UserDtoModel";
import { PasswordField } from '@hilla/react-components/PasswordField.js';


export default function HelloWorldView() {
    const [name, setName] = useState('');
    const [cryptoCurrencies, setCryptoCurrencies] = useState<CryptoCurrencyRecord[]>([]);

    useEffect(() => {
        CryptoCurrencyService.getAllWithPrice()
            .then(setCryptoCurrencies)
            .catch(error => {
                Notification.show(error.response.error, { theme: 'error' });
            });
    }, []);

    const handleOnSuccess = () => {
        window.location.href = '/login';
    }

    const handleOnSubmitError = ({ error }: { error: any }) => {
        const json = JSON.stringify(error);
        console.log(error)
        console.log(json)
        Notification.show('Error while submitting: ' + json);
    }

    return (
        <>
            <section>
                <div className="p-m flex gap-m">
                    <Grid
                        items={cryptoCurrencies}>

                        <GridColumn path="name"/>
                        <GridColumn path="symbol"/>
                        <GridColumn path="price"/>
                    </Grid>
                </div>
            </section>
            <section>
                <AutoForm
                    service={UserService}
                    model={UserDtoModel}
                    fieldOptions={{
                        password: {
                            renderer: ({ field }) => <PasswordField {...field}  label="Password" />
                        },
                    }}
                    onSubmitSuccess={handleOnSuccess}
                    onSubmitError={handleOnSubmitError}
                />
            </section>
        </>
    );
}
