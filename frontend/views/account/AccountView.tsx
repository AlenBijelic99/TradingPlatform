import React, {useState, useEffect} from 'react';
import {useAuth} from 'Frontend/util/auth';
import {Accordion} from '@hilla/react-components/Accordion.js';
import {AccordionPanel} from '@hilla/react-components/AccordionPanel.js';
import {VerticalLayout} from '@hilla/react-components/VerticalLayout';
import {TextField} from '@hilla/react-components/TextField';
import {Button} from '@hilla/react-components/Button.js';
import {useForm} from '@hilla/react-form';
import UserModel from 'Frontend/generated/ch/heigvd/application/data/entities/UserModel';
import {UserEndpoint, UserService} from 'Frontend/generated/endpoints';
import User from 'Frontend/generated/ch/heigvd/application/data/entities/User';

export default function AccountView() {
    const {state} = useAuth();
    let [currentUser, setCurrentUser] = useState<User>();
    const [editMode, setEditMode] = useState(false);
    // Define a form using the useForm hook
    const {field, model, submit, read} = useForm(UserModel, {onSubmit});

    // Read user data into the form when component mounts
    useEffect(() => {
        return read(state.user || null);
    }, [state.user]);

    useEffect(() => {
        UserEndpoint.getAuthenticatedUser().then((user) => {
            setCurrentUser(user);
        });
    }, []);

    async function onSubmit(contact: User) {
        try {
            // Submit the updated user profile to the backend
            UserEndpoint.getAuthenticatedUser().then((user) => {
                console.log(user);

            });
            currentUser = contact;
            //TODO : fix this. The user is not updated in the backend
            await UserService.update(currentUser);
            setEditMode(false); // Exit edit mode after successful update
        } catch (error) {
            console.error('Error during update:', error);
        }
    }

    return (
        <Accordion>
            <AccordionPanel summary="Personal information">
                {editMode ? (
                    // Render form in edit mode
                    <VerticalLayout>
                        <TextField label="Username" {...field(model.username)} />
                        <TextField label="First Name" {...field(model.firstName)} />
                        <TextField label="Last Name" {...field(model.lastName)} />
                        <TextField label="Funds" {...field(model.funds)} />

                        <div className="flex gap-m">
                            <Button onClick={() => submit()} theme="primary">
                                Save
                            </Button>
                            <Button onClick={() => setEditMode(false)}>Cancel</Button>
                        </div>
                    </VerticalLayout>
                ) : (
                    // Render read-only view
                    <VerticalLayout>
                        <img style={{width: '200px'}} src="images/empty-plant.png" alt="Profile"/>
                        <span>Username: {state.user?.username}</span>
                        <span>Firstname: {state.user?.firstName}</span>
                        <span>Lastname: {state.user?.lastName}</span>
                        <span>Firstname: {state.user?.funds}</span>

                        <Button onClick={() => setEditMode(true)}>Edit</Button>
                    </VerticalLayout>
                )}
            </AccordionPanel>
            <AccordionPanel summary="Current crypto chart ">
            </AccordionPanel>
        </Accordion>
    );
}
